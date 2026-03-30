package tn.chouflifilm.services.Association;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WikipediaService {

    private static final String WIKI_SUMMARY_URI = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    private static final String WIKI_SEARCH_URI = "https://en.wikipedia.org/w/api.php?action=query&list=search&format=json&srsearch=";
    private static final String WIKI_CONTENT_URI = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts|pageimages&exintro=1&format=json&pilimit=1&piprop=thumbnail&pithumbsize=500&titles=";
    private static final String DBPEDIA_URI = "https://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=";

    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Récupère des informations complètes sur un sujet en combinant plusieurs sources
     *
     * @param searchTerm Le terme à rechercher
     * @return Map contenant toutes les données ou null en cas d'erreur
     */
    public static Map<String, Object> getComprehensiveInfo(String searchTerm) {
        Map<String, Object> result = new HashMap<>();
        try {
            // Étape 1: Recherche sur Wikipedia pour trouver les pages pertinentes
            List<Map<String, Object>> searchResults = searchWikipedia(searchTerm);
            if (searchResults.isEmpty()) {
                return null;
            }

            result.put("searchResults", searchResults);

            // Étape 2: Obtenir le résumé détaillé de la première correspondance
            String mostRelevantTitle = (String) searchResults.get(0).get("title");
            Map<String, Object> summary = getSummary(mostRelevantTitle);
            if (summary != null) {
                result.put("primaryInfo", summary);
            }

            // Étape 3: Obtenir du contenu plus détaillé pour cette page
            Map<String, Object> detailedContent = getDetailedContent(mostRelevantTitle);
            if (detailedContent != null) {
                result.put("detailedContent", detailedContent);
            }

            // Étape 4: Essayer d'obtenir des informations supplémentaires de DBpedia
            Map<String, Object> dbpediaInfo = getDBpediaInfo(mostRelevantTitle);
            if (dbpediaInfo != null && !dbpediaInfo.isEmpty()) {
                result.put("additionalInfo", dbpediaInfo);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Recherche des pages Wikipedia correspondant au terme de recherche
     */
    private static List<Map<String, Object>> searchWikipedia(String searchTerm) throws IOException, InterruptedException {
        String encodedSearch = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WIKI_SEARCH_URI + encodedSearch))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> results = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray searchResults = jsonResponse.getJSONObject("query").getJSONArray("search");

            for (int i = 0; i < searchResults.length() && i < 5; i++) {
                JSONObject item = searchResults.getJSONObject(i);
                Map<String, Object> resultItem = new HashMap<>();
                resultItem.put("title", item.getString("title"));
                resultItem.put("snippet", item.getString("snippet").replaceAll("<[^>]*>", ""));
                results.add(resultItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }


    public static Map<String, Object> getSummary(String title) {
        String formattedTitle = URLEncoder.encode(title.replace(' ', '_'), StandardCharsets.UTF_8);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(WIKI_SUMMARY_URI + formattedTitle))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            Map<String, Object> result = new HashMap<>();
            JSONObject jsonData = new JSONObject(response.body());

            result.put("title", jsonData.optString("title", ""));
            result.put("extract", jsonData.optString("extract", ""));
            result.put("description", jsonData.optString("description", ""));

            if (!jsonData.has("thumbnail")) {
                Map<String, String> thumbnail = new HashMap<>();
                thumbnail.put("source", "/resources/images/default_image.jpg");
                result.put("thumbnail", thumbnail);
            } else {
                JSONObject thumbnailObj = jsonData.getJSONObject("thumbnail");
                Map<String, String> thumbnail = new HashMap<>();
                thumbnail.put("source", thumbnailObj.optString("source", ""));
                result.put("thumbnail", thumbnail);
            }

            // Ajouter l'URL pour accéder à la page complète
            if (jsonData.has("content_urls") && jsonData.getJSONObject("content_urls").has("desktop")) {
                result.put("pageUrl", jsonData.getJSONObject("content_urls").getJSONObject("desktop").getString("page"));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère un contenu plus détaillé de Wikipedia
     */
    private static Map<String, Object> getDetailedContent(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);

        try {
            // Notez que j'encode manuellement le pipe | en %7C dans l'URL
            String url = WIKI_CONTENT_URI.replace("|", "%7C") + encodedTitle;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");
            String firstPageId = pages.keys().next();
            JSONObject pageData = pages.getJSONObject(firstPageId);

            Map<String, Object> result = new HashMap<>();
            result.put("pageId", firstPageId);
            result.put("fullExtract", pageData.optString("extract", "").replaceAll("<[^>]*>", ""));

            if (pageData.has("thumbnail")) {
                result.put("highResImage", pageData.getJSONObject("thumbnail").optString("source", ""));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère des informations complémentaires de DBpedia
     */
    private static Map<String, Object> getDBpediaInfo(String title) {
        String sparqlQuery = "SELECT ?property ?value WHERE { " +
                "<http://dbpedia.org/resource/" + title.replace(' ', '_') + "> ?property ?value . " +
                "FILTER(LANG(?value) = 'en' || LANG(?value) = '')" +
                "} LIMIT 20";

        String encodedQuery = URLEncoder.encode(sparqlQuery, StandardCharsets.UTF_8);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DBPEDIA_URI + encodedQuery))
                    .header("Accept", "application/sparql-results+json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray results = jsonResponse.getJSONObject("results").getJSONArray("bindings");

            Map<String, Object> dbpediaData = new HashMap<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject binding = results.getJSONObject(i);
                String propertyUri = binding.getJSONObject("property").getString("value");
                String propertyName = propertyUri.substring(propertyUri.lastIndexOf('/') + 1);

                // Ne garder que les propriétés pertinentes
                if (propertyName.equals("abstract") ||
                        propertyName.equals("thumbnail") ||
                        propertyName.equals("website") ||
                        propertyName.contains("date") ||
                        propertyName.contains("location")) {

                    String value = binding.getJSONObject("value").getString("value");
                    dbpediaData.put(propertyName, value);
                }
            }

            return dbpediaData;

        } catch (Exception e) {
            // Si DBpedia ne fonctionne pas, ce n'est pas critique
            return new HashMap<>();
        }
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
            Map<String, Object> comprehensiveInfo = getComprehensiveInfo("Association Basma");

        if (comprehensiveInfo != null) {
            System.out.println("=== RÉSULTATS DE RECHERCHE ===");
            List<Map<String, Object>> searchResults = (List<Map<String, Object>>) comprehensiveInfo.get("searchResults");
            for (Map<String, Object> result : searchResults) {
                System.out.println("Titre: " + result.get("title"));
                System.out.println("Aperçu: " + result.get("snippet"));
                System.out.println("-----------");
            }

            if (comprehensiveInfo.containsKey("primaryInfo")) {
                Map<String, Object> primaryInfo = (Map<String, Object>) comprehensiveInfo.get("primaryInfo");
                System.out.println("\n=== INFORMATION PRINCIPALE ===");
                System.out.println("Titre: " + primaryInfo.get("title"));
                System.out.println("Description: " + primaryInfo.get("description"));
                System.out.println("Résumé: " + primaryInfo.get("extract"));
                System.out.println("Image: " + ((Map<?, ?>)primaryInfo.get("thumbnail")).get("source"));
            }

            if (comprehensiveInfo.containsKey("detailedContent")) {
                Map<String, Object> detailedContent = (Map<String, Object>) comprehensiveInfo.get("detailedContent");
                System.out.println("\n=== CONTENU DÉTAILLÉ ===");
                System.out.println("ID de page: " + detailedContent.get("pageId"));
                System.out.println("Extrait complet (tronqué): " +
                        ((String)detailedContent.get("fullExtract")).substring(0,
                                Math.min(200, ((String)detailedContent.get("fullExtract")).length())) + "...");
            }

            if (comprehensiveInfo.containsKey("additionalInfo")) {
                Map<String, Object> additionalInfo = (Map<String, Object>) comprehensiveInfo.get("additionalInfo");
                System.out.println("\n=== INFORMATIONS SUPPLÉMENTAIRES (DBpedia) ===");
                for (Map.Entry<String, Object> entry : additionalInfo.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        } else {
            System.out.println("Aucune information trouvée");
        }
    }
}