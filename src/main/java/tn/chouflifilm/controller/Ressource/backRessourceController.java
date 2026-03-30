package tn.chouflifilm.controller.Ressource;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.ressource;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.services.Ressources.ressourceService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class backRessourceController {
    @FXML
    private TableColumn<ressource, String> nomColumn;

    @FXML
    private TableColumn<ressource, String> prenomColumn;

    @FXML
    private TableColumn<ressource, String> nomAssociationColumn;
    @FXML
    private TableColumn<ressource, Void> actionsColumn1;
    @FXML
    private TableColumn<ressource, Void> actionsColumn;
    @FXML
    private TableColumn<ressource, String> emailColumn;


    @FXML
    private Circle circle1;


    private tn.chouflifilm.services.Ressources.ressourceService ressourceService = new ressourceService();

    @FXML
    private TableView<ressource> tableView;

    private Association association;

    public void setAssociation(Association association) throws SQLException {

this.association =association;

        Platform.runLater(() -> {
            List<ressource> ressources = null;
            try {
                ressources = ressourceService.getRessourceParAssociation(association);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ObservableList<ressource> observableUsers = FXCollections.observableArrayList(ressources);
        tableView.setItems(observableUsers);

        nomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getNom())
        );
        prenomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getPrenom())
        );
        nomAssociationColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAssociation().getNom())
        );
        emailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getEmail()
        ));

        actionsColumn1.setCellFactory(column -> new TableCell<ressource, Void>() {
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
                    ressource res = getTableRow().getItem();
                    String imagePath = res.getUser().getimage(); // exemple : "C:\\Users\\21694\\Downloads\\test.jpg"

                    try {
                        Image image;

                        if (imagePath.startsWith("http") || imagePath.startsWith("file:/")) {
                            image = new Image(imagePath); // pour les URLs ou chemins déjà convertis
                        } else {
                            image = new Image(new File(imagePath).toURI().toString()); // depuis le disque
                        }

                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
                        setGraphic(null);
                    }
                }
            }

        });


        actionsColumn.setCellFactory(column -> new TableCell<ressource, Void>() {
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
                    ressource res = getTableRow().getItem();

System.out.println(res.getAssociation().getImage());
                    System.out.println(res.getAssociation().getNom());

                    String imagePath = res.getAssociation().getImage();

                    try {
                        Image image;

                        if (imagePath.startsWith("http") || imagePath.startsWith("file:/")) {
                            image = new Image(imagePath);
                        } else {
                            image = new Image(new File(imagePath).toURI().toString());
                        }

                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
                        setGraphic(null);
                    }
                }
            }
        });





        });

    }








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

}
