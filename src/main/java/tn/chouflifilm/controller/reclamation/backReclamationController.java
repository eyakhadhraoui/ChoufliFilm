package tn.chouflifilm.controller.reclamation;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Reclamation;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TableView;
import tn.chouflifilm.entities.Reponse;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.EmailSender;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
import tn.chouflifilm.services.Reponse.serviceReponse;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;

public class backReclamationController {
    @FXML
    private Circle circle1;
    @FXML
    private TableView<Reclamation> tableView;
    @FXML
    private TableColumn<Reclamation, String> nomColumn;
    @FXML
    private TableColumn<Reclamation, String> emailColumn;
    @FXML
    private TableColumn<Reclamation, String> prenomColumn;

    @FXML
    private TableColumn<Reclamation, Void> actionsColumn;
    @FXML
    private TableColumn<Reclamation, String> actionsColumn1;
    @FXML
    private TableColumn<Reclamation, Void> actionsColumnactions;
    private tn.chouflifilm.services.userService userService = new userService();

    // Chatbot UI components
    @FXML
    private AnchorPane chatWindow;

    @FXML
    private VBox chatBody;

    @FXML
    private TextField userInput;

    @FXML
    private ScrollPane chatScrollPane;

    serviceReclamation serviceReclamation = new serviceReclamation();

    serviceReponse serviceReponse= new serviceReponse();

    // API Key for Gemini - You should store this more securely in a production environment
    private final String API_KEY = "AIzaSyCD203_ROXc1k6kEnAI0xXFVQBSUTwnmg0";
    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    // Keywords list for filtering relevant questions
    private final List<String> keywordsReclamation = Arrays.asList(
            "film", "cinéma", "séance", "ticket", "billet", "réservation", "place",
            "remboursement", "annulation", "projection", "salle", "écran", "son",
            "qualité", "retard", "annulé", "problème", "réclamation", "plainte", "reponse"
    );

    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) tableView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }

    @FXML
    public void initialize() throws SQLException {
        // Existing initialization code
        List<Reclamation> list = serviceReclamation.getListReclamations();
        System.out.println(list);
        for(Reclamation r : list){
            if (r.getReponse() == null){
                System.out.println("null");
            } else {
                System.out.println(r.getReponse().getReponse());
            }
        }
        ObservableList<Reclamation> observableList = FXCollections.observableArrayList(list);
        tableView.setItems(observableList);
        nomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getNom())
        );
        prenomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getPrenom())
        );

        actionsColumn1.setCellValueFactory(cellData -> {
            if (cellData.getValue().getReponse() == null) {
                return new SimpleStringProperty("Pas de Réponse");
            } else {
                return new SimpleStringProperty(cellData.getValue().getReponse().getReponse());
            }
        });

        actionsColumn1.setCellFactory(column -> new TableCell<Reclamation, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equals("Pas de Réponse")) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    }
                }
            }
        });

        emailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getEmail()
                ));

        actionsColumn.setCellFactory(column -> new TableCell<Reclamation, Void>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitHeight(75);
                imageView.setFitWidth(60);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Reclamation res = getTableRow().getItem();
                    String imagePath = res.getImage();

                    try {
                        Image image = new Image(getClass().getResourceAsStream(imagePath));
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        Image image = new Image(getClass().getResourceAsStream("/images/cinema.jpg"));
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            }
        });

        actionsColumnactions.setCellFactory(param -> new TableCell<Reclamation, Void>() {
            private final Button updateButton = new Button();
            private final Button supprimerButton = new Button();
            private final HBox pane = new HBox(10, updateButton, supprimerButton);

            {
                supprimerButton.setOnAction(event -> {
                    // Récupérer la réclamation de la ligne
                    Reclamation reclamation = getTableView().getItems().get(getIndex());

                    if (reclamation != null) {
                        try {
                            Reponse reponse = new Reponse("Réclamation Rejeté",reclamation.getId());
                            serviceReponse.ajouterReponse(reponse);
                            serviceReclamation.repondreReclamation("rejeté", reclamation.getId());
                            User user = userService.recherparid(reclamation.getUser_id());
                            EmailSender.sendEmail(
                                    "alpha2025group@gmail.com",
                                    "hrrjbkauhdctymvk",
                                    user.getEmail(),
                                    "Reponse a reclamation",
                                    "Réclamation Rejeté"
                            );

                            initialize();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            {
                updateButton.setOnMouseClicked(event -> {
                    try {
                        Reclamation reclamation = getTableView().getItems().get(getIndex());
                        UserSessionManager.getInstance().setReclamation(reclamation);
                        pageReponse();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Reclamation reclamation = (Reclamation) getTableRow().getItem();
                    if (reclamation.getReponse() != null) {
                        setGraphic(null);
                    } else {
                        FontAwesomeIconView replyIcon = new FontAwesomeIconView(FontAwesomeIcon.REPLY);
                        replyIcon.setSize("16");
                        updateButton.setGraphic(replyIcon);
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setSize("16");
                        supprimerButton.setGraphic(deleteIcon);
                        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
                        supprimerButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
                        setGraphic(pane);
                    }
                }
            }
        });

        // Initialize chat window if it exists in the FXML
        if (chatWindow != null) {
            chatWindow.setVisible(false);

            // Add event handler for key press if userInput exists
            if (userInput != null) {
                userInput.setOnKeyPressed(this::handleKeyPress);
            }

            // Add welcome message if chatBody exists
            if (chatBody != null) {
                displayBotMessage("Bonjour! Je suis l'assistant de réclamations de ChoufliFilm. Comment puis-je vous aider aujourd'hui?");
            }
        }
    }

    // Chatbot methods
    @FXML
    public void toggleChatbot() {
        if (chatWindow != null) {
            chatWindow.setVisible(!chatWindow.isVisible());
            if (chatWindow.isVisible() && userInput != null) {
                userInput.requestFocus();
            }
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    @FXML
    public void sendMessage() {
        if (userInput == null || chatBody == null) return;

        String message = userInput.getText().trim();
        if (message.isEmpty()) return;

        // Display user message
        displayUserMessage(message);
        userInput.clear();

        // Check if question is relevant to cinema complaint system
        boolean isRelevant = keywordsReclamation.stream()
                .anyMatch(keyword -> message.toLowerCase().contains(keyword.toLowerCase()));

        // Display thinking message
        Text thinkingText = new Text("En train de réfléchir...");
        thinkingText.setFont(Font.font("System", 12));
        thinkingText.setFill(Color.GRAY);

        TextFlow thinkingFlow = new TextFlow(thinkingText);
        HBox thinkingBox = new HBox(thinkingFlow);
        thinkingBox.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> {
            chatBody.getChildren().add(thinkingBox);
            scrollToBottom();
        });

        // Simulate thinking time
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            if (isRelevant) {
                // Process with Gemini API
                CompletableFuture.runAsync(() -> {
                    try {
                        String botResponse = callGeminiAPI(message);

                        // Remove thinking message and display actual response
                        Platform.runLater(() -> {
                            chatBody.getChildren().remove(thinkingBox);
                            displayBotMessage(botResponse);
                        });
                    } catch (Exception ex) {
                        Platform.runLater(() -> {
                            chatBody.getChildren().remove(thinkingBox);
                            displayBotMessage("Désolé, je ne peux pas répondre pour le moment. Erreur technique.");
                        });
                        ex.printStackTrace();
                    }
                });
            } else {
                // Not relevant to cinema complaints
                Platform.runLater(() -> {
                    chatBody.getChildren().remove(thinkingBox);
                    displayBotMessage("Désolé, je ne peux répondre qu'aux questions concernant les réclamations cinématographiques. Veuillez poser une question en rapport avec le cinéma, les films, les billets, ou les problèmes de projection.");
                });
            }
        });
        pause.play();
    }

    private void displayUserMessage(String message) {
        if (chatBody == null) return;

        Text text = new Text(message);
        text.setFont(Font.font("System", 14));
        text.setFill(Color.BLACK);

        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: #DCF8C6; -fx-background-radius: 15px; -fx-padding: 8px;");

        HBox hbox = new HBox(textFlow);
        hbox.setAlignment(Pos.CENTER_RIGHT);

        Platform.runLater(() -> {
            chatBody.getChildren().add(hbox);
            scrollToBottom();
        });
    }

    private void displayBotMessage(String message) {
        if (chatBody == null) return;

        Text text = new Text(message);
        text.setFont(Font.font("System", 14));
        text.setFill(Color.BLACK);

        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: #F1F0F0; -fx-background-radius: 15px; -fx-padding: 8px;");

        HBox hbox = new HBox(textFlow);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> {
            chatBody.getChildren().add(hbox);
            scrollToBottom();
        });
    }

    private void scrollToBottom() {
        if (chatScrollPane != null) {
            chatScrollPane.setVvalue(1.0);
        }
    }

    private String callGeminiAPI(String userMessage) throws Exception {
        URL url = new URL(API_URL + "?key=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Prepare request JSON
        String requestBody = "{"
                + "\"contents\": [{"
                + "\"parts\": [{"
                + "\"text\": \"" + userMessage.replace("\"", "\\\"") + "\""
                + "}]"
                + "}]"
                + "}";

        // Send request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        JSONObject firstCandidate = candidates.getJSONObject(0);
        JSONObject content = firstCandidate.getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        JSONObject firstPart = parts.getJSONObject(0);

        return firstPart.getString("text");
    }

    // Existing navigation methods
    public void pagedashboard() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("user.fxml", currentStage);
    }

    public void pageuser() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("backback.fxml", currentStage);
    }

    public void pageAssociation() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageBack.fxml", currentStage);
    }

    public void pageReponse() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Reponse/addReponse.fxml", currentStage);
    }
}