package tn.chouflifilm.controller.Association;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.IOException;

public class wikipediaController {

    @FXML
    public void initialize() {


    }

    @FXML
    private ImageView imageView;
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
