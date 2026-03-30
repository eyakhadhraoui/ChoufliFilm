package tn.chouflifilm.controller.user;

import com.gluonhq.maps.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;

public class mapcontroller implements Initializable {

    @FXML
    private VBox address;

    private double latitude = 36.8065;
    private double longitude = 10.1815;

    private final MapPoint mapPoint = new MapPoint(48.8583701, 2.2944813);

    MapView  mapView = createMapView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
        mapView.setOnMouseClicked(this::handleMapClick);

    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(300, 300);
        mapView.setMinSize(300, 300);
        mapView.setMaxSize(300, 300);

        mapView.setZoom(5);
        mapView.setCenter(mapPoint);


        return mapView;
    }





    private List<CustomMarkerLayer> markerLayers = new ArrayList<>();
    // Variable pour stocker la couche de marqueur actuelle
    private CustomMarkerLayer currentMarkerLayer = null;

    private String  handleMapClick(MouseEvent event) {
        MapPoint mapPoint = mapView.getMapPosition(event.getX(), event.getY());
        latitude = mapPoint.getLatitude();
        longitude = mapPoint.getLongitude();

        // Afficher les coordonnées dans la console
        System.out.println("Coordonnées du clic:");
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);

        // Supprimer l'ancien marqueur s'il existe
        if (currentMarkerLayer != null) {
            mapView.removeLayer(currentMarkerLayer);
        }

        // Ajouter un nouveau marqueur rouge à l'endroit du clic
        addMarker(new MapPoint(latitude, longitude));

        String address = reverseGeocode(latitude, longitude);

        // Afficher l'adresse dans la console
        System.out.println("Adresse : " + address);

        // Forcer le rafraîchissement de la carte
        mapView.flyTo(0, mapPoint, 0.1);
        return  address;
    }

    // Méthode pour ajouter un marqueur sur la carte
    private void addMarker(MapPoint point) {
        // Créer un cercle rouge pour représenter le marqueur
        Circle circle = new Circle(7, Color.RED);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);

        // Créer un StackPane pour contenir le cercle
        StackPane marker = new StackPane(circle);
        marker.setPadding(new Insets(0));
        marker.setAlignment(Pos.CENTER);

        // Créer une nouvelle couche de marqueur
        CustomMarkerLayer markerLayer = new CustomMarkerLayer(point, marker);

        // Ajouter la couche à la carte
        mapView.addLayer(markerLayer);

        // Stocker la référence à la couche actuelle
        currentMarkerLayer = markerLayer;

        // Forcer la mise à jour de la disposition de la couche
        markerLayer.layoutLayer();
    }

    // Classe pour gérer les marqueurs personnalisés
    private class CustomMarkerLayer extends MapLayer {
        private final MapPoint point;
        private final Node marker;

        public CustomMarkerLayer(MapPoint point, Node marker) {
            this.point = point;
            this.marker = marker;
            this.getChildren().add(marker);
        }

        @Override
        protected void layoutLayer() {
            // Convertir les coordonnées géographiques en coordonnées d'écran
            Point2D mapPosition = getMapPoint(point.getLatitude(), point.getLongitude());

            // Positionner le marqueur
            marker.setLayoutX(mapPosition.getX() - marker.prefWidth(-1) / 2);
            marker.setLayoutY(mapPosition.getY() - marker.prefHeight(-1) / 2);
        }
    }

    private String reverseGeocode(double latitude, double longitude) {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Nibrass", "TripEase/1.0")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                String displayName = jsonObject.optString("display_name", "Unknown Location");

                JSONObject address = jsonObject.optJSONObject("address");
                if (address != null) {
                    String road = address.optString("road", "");
                    String city = address.optString("city", "");
                    String country = address.optString("country", "");
                    String postcode = address.optString("postcode", "");

                    String userFriendlyAddress = String.join(", ", road, city, postcode, country);
                    return userFriendlyAddress.isEmpty() ? displayName : userFriendlyAddress;
                }

                return displayName;
            } else {
                return "Error: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }



}
