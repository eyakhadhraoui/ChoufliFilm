package tn.chouflifilm.controller.Association;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class affichageFrontController
{
    private final tn.chouflifilm.services.userService userService = new userService();
    private final tn.chouflifilm.services.Association.associationService associationService = new associationService();
    @FXML
    private ImageView imageView;
    @FXML
    private VBox reclamationContainer;


    @FXML
    public void initialize() throws SQLException {

        String imageFromDb = UserSessionManager.getInstance().getCurrentUser().getimage();
        Image image1 = new Image(imageFromDb);
        imageView.setImage(image1);

        // Appliquer un clip circulaire
        Circle clipCircle = new Circle(
                imageView.getFitWidth() / 2,
                imageView.getFitHeight() / 2,
                Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2
        );
        imageView.setClip(clipCircle);


        reclamationContainer.getChildren().clear();

        Label mainTitle = new Label("ASSOCIATIONS");
        mainTitle.setStyle("-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        mainTitle.setPrefWidth(Double.MAX_VALUE);
        mainTitle.setAlignment(Pos.CENTER);
        reclamationContainer.getChildren().add(mainTitle);

        Separator titleSeparator = new Separator();
        titleSeparator.setStyle("-fx-background-color: linear-gradient(to right, transparent, #3498db, transparent); -fx-opacity: 0.8;");
        titleSeparator.setPrefWidth(300);
        reclamationContainer.getChildren().add(titleSeparator);

        Region spacer = new Region();
        spacer.setPrefHeight(25);
        reclamationContainer.getChildren().add(spacer);

        VBox listContainer = new VBox(15);
        listContainer.setPadding(new Insets(5, 5, 20, 5));

        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefViewportHeight(350);
        scrollPane.getStyleClass().add("edge-to-edge");

        List<Association> list = associationService.afficherAssociations();
        if (list.isEmpty()) {
            Label emptyLabel = new Label("Aucune association disponible");
            emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #666; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
            emptyLabel.setAlignment(Pos.CENTER);
            listContainer.getChildren().add(emptyLabel);
        } else {
            for (Association association : list) {
                VBox itemContainer = new VBox(12);

                // Professional calm blue gradient background
                itemContainer.setStyle(
                        "-fx-background-color: linear-gradient(to bottom right, #e6f2ff, #c1d9f1, #a3c2e6); " +
                                "-fx-padding: 20; " +
                                "-fx-background-radius: 12; " +
                                "-fx-border-radius: 12; " +
                                "-fx-border-color: #7ba4d0; " +
                                "-fx-border-width: 0.5;"
                );

                // Refined shadow effect
                DropShadow shadow = new DropShadow();
                shadow.setRadius(10);
                shadow.setSpread(0.05);
                shadow.setColor(Color.rgb(0, 0, 0, 0.15));
                shadow.setOffsetY(3);
                itemContainer.setEffect(shadow);

                HBox contentLayout = new HBox(20);
                contentLayout.setAlignment(Pos.CENTER_LEFT);

                ImageView imageView = new ImageView();
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);

                // Create rounded image style
                Rectangle clip = new Rectangle(150, 150);
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                imageView.setClip(clip);

                // Enhanced shadow for image
                DropShadow imageShadow = new DropShadow();
                imageShadow.setRadius(8);
                imageShadow.setColor(Color.rgb(0, 0, 0, 0.3));
                imageShadow.setOffsetY(2);
                imageView.setEffect(imageShadow);
                Image image;
                try {
                    String imageName = association.getImage();
                    if (imageName != null && !imageName.isEmpty()) {
                        // Vérifier si c'est un chemin absolu (commence par C:, D:, etc.)
                        if (imageName.matches("^[A-Za-z]:\\\\.*")) {
                            // C'est un chemin absolu local, utiliser File pour le charger
                            File imageFile = new File(imageName);
                            image = new Image(imageFile.toURI().toString());
                        } else {
                            // C'est un chemin relatif, utiliser getResourceAsStream
                            image = new Image(getClass().getResourceAsStream(imageName));
                        }

                        if (image.isError()) throw new Exception("Image not found");
                        imageView.setImage(image);
                    } else {
                        throw new Exception("No image name provided");
                    }
                } catch (Exception e) {
                    image = new Image(getClass().getResourceAsStream("/images/inconnu.jpg"));
                    imageView.setImage(image);
                }

                Region spaceBetween = new Region();
                spaceBetween.setPrefWidth(30);

                VBox infoBox = new VBox(16);
                infoBox.setPadding(new Insets(5, 0, 5, 0));

                // Text styles for better visibility on blue background
                String labelStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1a3c5e; -fx-font-family: 'Segoe UI', Arial, sans-serif;";
                String valueStyle = "-fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif;";

                // Association information
                infoBox.getChildren().add(createInfoRow("Description:", new Text(association.getDescription()), labelStyle, valueStyle));
                infoBox.getChildren().add(createInfoRow("Nom:", new Label(association.getNom()), labelStyle, valueStyle));
                infoBox.getChildren().add(createInfoRow("Adresse:", new Label(association.getAdresse()), labelStyle, valueStyle));
                infoBox.getChildren().add(createInfoRow("Email:", new Label(association.getMail_association()), labelStyle, valueStyle));

                // Button container
                HBox actionButtons = new HBox(15);
                actionButtons.setAlignment(Pos.CENTER_RIGHT);
                actionButtons.setPadding(new Insets(10, 0, 0, 0));

                // Professional blue "Aider" button with refined styling
                Button aiderButton = new Button("Aider");
                Button wikipediaButton = new Button("Search On Wikipedia");
                wikipediaButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #1e88e5, #1565c0); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 30; " +
                                "-fx-padding: 10 25; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1); " +
                                "-fx-cursor: hand; " +
                                "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                );

                aiderButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #1e88e5, #1565c0); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 30; " +
                                "-fx-padding: 10 25; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1); " +
                                "-fx-cursor: hand; " +
                                "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                );

                wikipediaButton.setOnMouseEntered(e ->
                        wikipediaButton.setStyle(
                                "-fx-background-color: linear-gradient(to bottom, #1976d2, #0d47a1); " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 16px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-background-radius: 30; " +
                                        "-fx-padding: 10 25; " +
                                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2); " +
                                        "-fx-cursor: hand; " +
                                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                        )
                );

                wikipediaButton.setOnMouseExited(e ->
                        wikipediaButton.setStyle(
                                "-fx-background-color: linear-gradient(to bottom, #1e88e5, #1565c0); " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 16px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-background-radius: 30; " +
                                        "-fx-padding: 10 25; " +
                                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1); " +
                                        "-fx-cursor: hand; " +
                                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                        )
                );

                // Hover effect for button
                aiderButton.setOnMouseEntered(e ->
                        aiderButton.setStyle(
                                "-fx-background-color: linear-gradient(to bottom, #1976d2, #0d47a1); " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 16px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-background-radius: 30; " +
                                        "-fx-padding: 10 25; " +
                                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2); " +
                                        "-fx-cursor: hand; " +
                                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                        )
                );

                aiderButton.setOnMouseExited(e ->
                        aiderButton.setStyle(
                                "-fx-background-color: linear-gradient(to bottom, #1e88e5, #1565c0); " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 16px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-background-radius: 30; " +
                                        "-fx-padding: 10 25; " +
                                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1); " +
                                        "-fx-cursor: hand; " +
                                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
                        )
                );

                aiderButton.setOnAction(e -> {
                    try {
                        System.out.println(association.getId());
                        UserSessionManager.getInstance().setAssociation(association);
                        pageressource();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                actionButtons.getChildren().add(aiderButton);
                actionButtons.getChildren().add(wikipediaButton);

                // Add everything to the layout
                contentLayout.getChildren().addAll(imageView, infoBox);
                itemContainer.getChildren().add(contentLayout);
                itemContainer.getChildren().add(actionButtons);
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

        if (valueNode instanceof Label)
            ((Label) valueNode).setStyle(valueStyle);
        else if (valueNode instanceof Text) {
            ((Text) valueNode).setStyle(valueStyle);
            ((Text) valueNode).setWrappingWidth(280);
        }

        row.getChildren().addAll(label, valueNode);
        return row;
    }

    private String formatDate(Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");
        return formatter.format(date);
    }

    public void pageressource() throws IOException {
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Ressource/addRessourceFont.fxml", currentStage);
    }

    public void pageupdateFront() throws IOException {
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/user/editFront.fxml", currentStage);
    }

    public void pageReclamation() throws IOException {
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Reclamation/affichageReclamationFront.fxml", currentStage);
    }

    public void pageassociation() throws IOException {
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageFront.fxml", currentStage);
    }

    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
}