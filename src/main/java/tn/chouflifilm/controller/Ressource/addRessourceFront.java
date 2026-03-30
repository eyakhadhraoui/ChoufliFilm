package tn.chouflifilm.controller.Ressource;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.entities.ressource;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
import tn.chouflifilm.services.Ressources.ressourceService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class addRessourceFront {
    private tn.chouflifilm.services.Ressources.ressourceService ressourceService = new ressourceService();
    @FXML
    private Label typeComboBoxErrorLabel;
    @FXML
    private ComboBox<String> typeComboBox;
    public void addRessource() throws SQLException, IOException {
        int compteur=0;
        String  besoin_specifique = typeComboBox.getValue();
        if(besoin_specifique == null||besoin_specifique.equals("")|| besoin_specifique.equals("Séléctionnez un besoin Spécifique")) {
showError("Ressource Invalide",typeComboBoxErrorLabel);
compteur++;
        }
else{
typeComboBoxErrorLabel.setOpacity(0);
        }
if(compteur==0) {
    User user = UserSessionManager.getInstance().getCurrentUser();
    ressource ressource = new ressource(UserSessionManager.getInstance().getAssociation().getId(), UserSessionManager.getInstance().getCurrentUser().getId(), besoin_specifique);
    ressourceService.ajouterRessource(ressource);
    this.pageassociation();
}
    }

    public void pageupdateFront() throws IOException {

        Stage currentStage = (Stage) typeComboBox.getScene().getWindow();
        navbarController.changeScene("/user/editFront.fxml", currentStage);
    }
    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) typeComboBox.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
    public void pageFront() throws IOException {

        Stage currentStage = (Stage) typeComboBox.getScene().getWindow();
        navbarController.changeScene("/user/front.fxml", currentStage);

    }
    public void pageassociation() throws IOException {

        Stage currentStage = (Stage) typeComboBox.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageFront.fxml", currentStage);
    }
    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }
    public void pageReclamation() throws IOException {

        Stage currentStage = (Stage) typeComboBox.getScene().getWindow();
        navbarController.changeScene("/Reclamation/affichageReclamationFront.fxml", currentStage);
    }

}
