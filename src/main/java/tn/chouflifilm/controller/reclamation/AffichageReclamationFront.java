package tn.chouflifilm.controller.reclamation;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.Reponse;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
import tn.chouflifilm.services.Reponse.serviceReponse;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class AffichageReclamationFront {
    private final userService userService = new userService();
    private final serviceReclamation serviceReclamation = new serviceReclamation();
    private final serviceReponse serviceReponse = new serviceReponse();

    @FXML
    private ImageView imageView;
    @FXML
    private VBox reclamationContainer;

    // Palette de couleurs professionnelle
    private final String PRIMARY_DARK = "#1a2a57";  // Bleu foncé
    private final String PRIMARY = "#2c4494";       // Bleu principal
    private final String PRIMARY_LIGHT = "#4267b2"; // Bleu clair (Facebook-like)
    private final String ACCENT = "#00b4d8";        // Bleu turquoise
    private final String TEXT_LIGHT = "#f0f6ff";    // Blanc bleuté
    private final String TEXT_DARK = "#2e3a59";     // Bleu très foncé pour texte
    private final String BG_LIGHT = "#f8faff";      // Fond légèrement bleuté

    @FXML
    public void initialize() throws SQLException, IOException {
        // Configuration de l'interface
        reclamationContainer.setStyle("-fx-background-color: linear-gradient(to bottom, " + BG_LIGHT + ", #e6eeff); -fx-padding: 20;");

        User user= userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());
        String imageFromDb = user.getimage();


        Image image1 = new Image(imageFromDb);
        imageView.setImage(image1);

        // Image circulaire avec un bord bleu
        Circle clipCircle = new Circle(
                imageView.getFitWidth() / 2,
                imageView.getFitHeight() / 2,
                Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2
        );
        imageView.setClip(clipCircle);

        // Effet d'ombre pour l'image de profil
        DropShadow profileShadow = new DropShadow();
        profileShadow.setRadius(15);
        profileShadow.setColor(Color.web(PRIMARY_DARK, 0.5));
        imageView.setEffect(profileShadow);

        // Bordure pour l'image de profil
        imageView.setStyle("-fx-border-color: " + PRIMARY_LIGHT + "; -fx-border-width: 3; -fx-border-radius: 50%;");

        reclamationContainer.getChildren().clear();

        // Création du conteneur pour le titre et le bouton d'ajout
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(200);

        // Titre principal stylisé
        Label mainTitle = new Label("RÉCLAMATIONS");
        mainTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));
        mainTitle.setStyle("-fx-text-fill: " + PRIMARY_DARK + ";");

        // Création de l'icône d'ajout
        Button addButton = new Button("+");
        addButton.setFont(Font.font("System", FontWeight.BOLD, 24));
        addButton.setStyle("-fx-background-color: " + PRIMARY_LIGHT + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50%;" +
                "-fx-min-width: 40px;" +
                "-fx-min-height: 40px;" +
                "-fx-max-width: 40px;" +
                "-fx-max-height: 40px;" +
                "-fx-padding: 0;" +
                "-fx-cursor: hand;");

        // Effet de survol pour le bouton
        addButton.setOnMouseEntered(e ->
                addButton.setStyle("-fx-background-color: " + ACCENT + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-min-width: 40px;" +
                        "-fx-min-height: 40px;" +
                        "-fx-max-width: 40px;" +
                        "-fx-max-height: 40px;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;")
        );
        addButton.setOnMouseClicked(e -> {
            try {
                pageReclamation();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        addButton.setOnMouseExited(e ->
                addButton.setStyle("-fx-background-color: " + PRIMARY_LIGHT + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-min-width: 40px;" +
                        "-fx-min-height: 40px;" +
                        "-fx-max-width: 40px;" +
                        "-fx-max-height: 40px;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;")
        );

        // Ajout d'un effet d'ombre
        DropShadow buttonShadow = new DropShadow();
        buttonShadow.setRadius(5);
        buttonShadow.setOffsetY(2);
        buttonShadow.setColor(Color.web("#000000", 0.3));
        addButton.setEffect(buttonShadow);

        // Action du bouton
        addButton.setOnAction(e -> {
            System.out.println("Ajouter une nouvelle réclamation");
            // Ajoutez ici le code pour ouvrir la fenêtre d'ajout de réclamation
        });

        titleBox.getChildren().addAll(mainTitle, addButton);
        reclamationContainer.getChildren().add(titleBox);

        // Séparateur stylisé
        Separator titleSeparator = new Separator();
        titleSeparator.setStyle("-fx-background-color: " + PRIMARY_LIGHT + "; -fx-pref-height: 3;");
        reclamationContainer.getChildren().add(titleSeparator);

        Region spacer = new Region();
        spacer.setPrefHeight(20);
        reclamationContainer.getChildren().add(spacer);

        VBox listContainer = new VBox(15);
        listContainer.setPadding(new Insets(10, 5, 10, 5));

        // Configuration du ScrollPane
        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 5;");
        scrollPane.setPrefViewportHeight(450);
        scrollPane.getStyleClass().add("edge-to-edge");

        List<Reclamation> list = serviceReclamation.getListReclamations(UserSessionManager.getInstance().getCurrentUser());
        System.out.println(list);
        if (list.isEmpty()) {
            VBox emptyBox = new VBox();
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40, 20, 40, 20));
            emptyBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 15;");

            Label emptyLabel = new Label("Aucune réclamation disponible");
            emptyLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 22));
            emptyLabel.setStyle("-fx-text-fill: " + PRIMARY_DARK + "; -fx-opacity: 0.8;");

            ImageView emptyIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
            emptyIcon.setFitHeight(100);
            emptyIcon.setFitWidth(100);
            emptyIcon.setPreserveRatio(true);
            emptyIcon.setOpacity(0.6);

            emptyBox.getChildren().addAll(emptyIcon, emptyLabel);
            listContainer.getChildren().add(emptyBox);
        } else {
            for (Reclamation reclamation : list) {
                System.out.println(reclamation.getImage());
                // Conteneur pour chaque réclamation
                VBox itemContainer = new VBox(10);

                // Dégradé de bleu pour les cartes de réclamation
                itemContainer.setStyle("-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_LIGHT + ");" +
                        "-fx-padding: 18; -fx-background-radius: 12; -fx-border-radius: 12; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.3); -fx-border-width: 1;");

                // Effet d'ombre pour la carte
                DropShadow cardShadow = new DropShadow();
                cardShadow.setRadius(12);
                cardShadow.setSpread(0.02);
                cardShadow.setColor(Color.web(PRIMARY_DARK, 0.4));
                itemContainer.setEffect(cardShadow);

                // Layout horizontal pour l'image et les infos
                HBox contentLayout = new HBox(20);
                contentLayout.setAlignment(Pos.CENTER_LEFT);

                // Configuration de l'image de la réclamation
                ImageView imageView = new ImageView();
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);

                // Cadre arrondi et ombre pour l'image
                StackPane imageFrame = new StackPane();
                imageFrame.setStyle("-fx-background-radius: 10; -fx-padding: 0; " +
                        "-fx-border-radius: 10; -fx-border-color: rgba(255, 255, 255, 0.5); -fx-border-width: 2;");

                DropShadow imageShadow = new DropShadow();
                imageShadow.setRadius(8);
                imageShadow.setColor(Color.web("#0a1428", 0.5));
                imageFrame.setEffect(imageShadow);

                Image image;
                try {
                    String imageName = reclamation.getImage();
                    System.out.println("**************************");
                    System.out.println(imageName);
                    if (imageName != null && !imageName.isEmpty()) {
                        image = new Image(getClass().getResourceAsStream(imageName));
                        if (image.isError()) throw new Exception("Image not found");
                        imageView.setImage(image);
                    } else {
                        throw new Exception("No image name provided");
                    }
                } catch (Exception e) {
                    String imageName = reclamation.getImage();
                    System.out.println("d5al lehn");
                    image = new Image(getClass().getResourceAsStream("/images/cinema.jpg"));
                    imageView.setImage(image);
                }

                // Appliquer le coin arrondi à l'image
                Rectangle clipRect = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
                clipRect.setArcWidth(20);
                clipRect.setArcHeight(20);
                imageView.setClip(clipRect);

                imageFrame.getChildren().add(imageView);

                Region spaceBetween = new Region();
                spaceBetween.setPrefWidth(30);

                // Boîte d'information
                VBox infoBox = new VBox(14);
                infoBox.setPadding(new Insets(8, 5, 8, 5));
                infoBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 8; -fx-padding: 15;");

                String labelStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_LIGHT + ";";
                String valueStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-text-fill: white;";

                // Ajout des champs de réclamation
                infoBox.getChildren().add(createInfoRow("Description:", new Text(reclamation.getDescription()), labelStyle, valueStyle));

                Label statusLabel = new Label(reclamation.getStatus());
                String statusColor;
                if (reclamation.getStatus().equalsIgnoreCase("En cours")) {
                    statusColor = "#ffd166"; // Jaune pour en cours
                } else if (reclamation.getStatus().equalsIgnoreCase("Résolu")) {
                    statusColor = "#06d6a0"; // Vert pour résolu
                } else {
                    statusColor = "#ef476f"; // Rouge pour autres statuts
                }
                statusLabel.setStyle(valueStyle + "-fx-background-color: " + statusColor + "; -fx-padding: 3 8; -fx-background-radius: 4;");

                infoBox.getChildren().add(createInfoRow("Status:", statusLabel, labelStyle, valueStyle));
                infoBox.getChildren().add(createInfoRow("Type:", new Label(reclamation.getType()), labelStyle, valueStyle));

                // Date avec icône
                HBox dateBox = new HBox(8);
                dateBox.setAlignment(Pos.CENTER_LEFT);
                ImageView clockIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
                clockIcon.setFitHeight(16);
                clockIcon.setFitWidth(16);
                Label dateLabel = new Label(formatDate(reclamation.getCreated_at()));
                dateLabel.setStyle(valueStyle);
                dateBox.getChildren().addAll(clockIcon, dateLabel);
                infoBox.getChildren().add(createInfoRow("Temps:", dateBox, labelStyle, valueStyle));

                // Réponse avec fond distinctif
                Reponse rep = serviceReponse.getReponse(reclamation);
                Text responseText;
                VBox responseBox = new VBox(5);
                if (rep != null) {
                    responseText = new Text(rep.getReponse());
                    responseBox.setStyle("-fx-background-color: #7FFF00; -fx-padding: 10; -fx-background-radius: 6;");
                } else {
                    responseText = new Text("   Pas de réponse actuellement   ");
                    responseBox.setStyle("-fx-background-color:#DC143C; -fx-padding: 10; -fx-background-radius: 6;");
                }
                responseText.setStyle(valueStyle);
                responseText.setWrappingWidth(280);
                responseBox.getChildren().add(responseText);
                infoBox.getChildren().add(createInfoRow("Réponse:", responseBox, labelStyle, valueStyle));

                // Création de boutons modifier et supprimer avec style amélioré
                HBox actionButtons = new HBox(15);
                actionButtons.setAlignment(Pos.CENTER_RIGHT);
                actionButtons.setPadding(new Insets(10, 5, 5, 5));

                Button modifyButton = new Button("Modifier");
                modifyButton.setStyle("-fx-background-color: " + ACCENT + "; -fx-text-fill: white; " +
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                        "-fx-padding: 8 20; -fx-cursor: hand;");
                modifyButton.setOnAction(e -> {

                    System.out.println("Modifier réclamation ID: " + reclamation.getId());
                });

                // Effet survol pour le bouton modifier
                modifyButton.setOnMouseEntered(e ->
                        modifyButton.setStyle("-fx-background-color: #00a0c0; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                                "-fx-padding: 8 20; -fx-cursor: hand;")
                );
                modifyButton.setOnMouseExited(e ->
                        modifyButton.setStyle("-fx-background-color: " + ACCENT + "; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                                "-fx-padding: 8 20; -fx-cursor: hand;")
                );

                Button deleteButton = new Button("Supprimer");
                deleteButton.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; " +
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                        "-fx-padding: 8 20; -fx-cursor: hand;");
                deleteButton.setOnAction(e -> {
                    try {
                        // Animation de suppression
                        FadeTransition fadeOut = new FadeTransition(Duration.millis(350), itemContainer);
                        fadeOut.setFromValue(1.0);
                        fadeOut.setToValue(0.0);
                        fadeOut.setOnFinished(event -> {
                            try {
                                serviceReclamation.supprimerReclamation(reclamation.getId());
                                initialize(); // Recharger la liste après suppression
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        fadeOut.play();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                modifyButton.setOnAction(e -> {


                            try {
                                UserSessionManager.getInstance().setReclamation(reclamation);
                                pageupdateReclamation();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }


                });











                // Effet survol pour le bouton supprimer
                deleteButton.setOnMouseEntered(e ->
                        deleteButton.setStyle("-fx-background-color: #c1121f; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                                "-fx-padding: 8 20; -fx-cursor: hand;")
                );
                deleteButton.setOnMouseExited(e ->
                        deleteButton.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 20; " +
                                "-fx-padding: 8 20; -fx-cursor: hand;")
                );

                if(rep==null){
                    actionButtons.getChildren().addAll(modifyButton,deleteButton);
                }
                if(rep!=null){
                    actionButtons.getChildren().addAll(deleteButton);
                }

                // Ajouter tout à la mise en page
                contentLayout.getChildren().addAll(imageFrame, spaceBetween, infoBox);
                itemContainer.getChildren().addAll(contentLayout, actionButtons);

                // Animation d'apparition pour chaque item
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), itemContainer);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                listContainer.getChildren().add(itemContainer);
            }
        }

        reclamationContainer.getChildren().add(scrollPane);
    }

    private HBox createInfoRow(String labelText, javafx.scene.Node valueNode, String labelStyle, String valueStyle) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText);
        label.setStyle(labelStyle);

        if (valueNode instanceof Label) {
            ((Label) valueNode).setStyle(valueStyle);
        } else if (valueNode instanceof Text) {
            ((Text) valueNode).setStyle(valueStyle);
            ((Text) valueNode).setWrappingWidth(280);
        }

        row.getChildren().addAll(label, valueNode);
        return row;
    }

    private String formatDate(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy ");
        return outputFormat.format(date);
    }

    // Classe utilitaire pour créer un rectangle avec coins arrondis
    private class Rectangle extends javafx.scene.shape.Rectangle {
        public Rectangle(double width, double height) {
            super(width, height);
        }
    }

    public void pageReclamation() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Reclamation/addReclamation.fxml", currentStage);
    }
    public void pageupdateFront() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/user/editFront.fxml", currentStage);
    }
    public void pageFront() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/user/front.fxml", currentStage);

    }

    public void pageupdateReclamation() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Reclamation/editReclamation.fxml", currentStage);

    }
    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
    public void pageassociation() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageFront.fxml", currentStage);
    }
}