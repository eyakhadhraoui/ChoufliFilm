package tn.chouflifilm.controller.Reponse;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Reponse;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.EmailSender;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
import tn.chouflifilm.services.Reponse.serviceReponse;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;
import tn.chouflifilm.entities.Reclamation;
import java.io.IOException;
import java.sql.SQLException;

public class reponseController {
    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private Circle circle4;
    @FXML
    private Circle circle5;

    @FXML
    private Circle circle6;

    @FXML
    private Circle circle7;

    @FXML
    private Circle circle8;

    @FXML
    private Circle circle9;
    @FXML
    private Circle circle10;

    @FXML
    private Circle circle11;

    @FXML
    private Circle circle12;
@FXML
   private  TextArea reponseTextArea;
@FXML
        private ComboBox<String> predefinedResponseComboBox;

@FXML
        private Label textAreaErrorLabel;
@FXML
        private Label comboBoxErrorLabel;
    private tn.chouflifilm.services.userService userService = new userService();
    serviceReclamation serviceReclamation = new serviceReclamation();

    serviceReponse serviceReponse= new serviceReponse();


    public void ajouterReponse() throws IOException, SQLException {
        int compteur=0;
        String reponseInput = reponseTextArea.getText();
        if(reponseInput.isEmpty()  || reponseInput.equals("") || reponseInput ==  null || reponseInput.length()<=3){
            compteur ++;
showError("Reponse Invalide",textAreaErrorLabel);
        }
        else {
            textAreaErrorLabel.setOpacity(0);
        }
if (compteur==0) {
    Reclamation reclamation = UserSessionManager.getInstance().getReclamation();
User user = userService.recherparid(reclamation.getUser_id());
System.out.println("User");
System.out.println(user);
    Reponse reponse = new Reponse(reponseInput, reclamation.getId());
    serviceReponse.ajouterReponse(reponse);
    serviceReclamation.repondreReclamation("Résolu", reclamation.getId());
    EmailSender.sendEmail(
            "alpha2025group@gmail.com",
            "hrrjbkauhdctymvk",
            user.getEmail(),
            "Reponse a reclamation",
            reponseInput
    );
    this.pageReclamation();
}
    }

public  void effacerEroorTextArea(){
        reponseTextArea.setText("");
    textAreaErrorLabel.setOpacity(0);
}
    public  void effacerEroorCombobox(){
        predefinedResponseComboBox.setValue("");
      comboBoxErrorLabel.setOpacity(0);
    }
    public void ajouterReponse2() throws IOException, SQLException {
int compteur =0;
        String reponseInput = predefinedResponseComboBox.getValue();
        if(reponseInput.isEmpty()  || reponseInput.equals("") || reponseInput == null || reponseInput.equals("") || reponseInput ==  null || reponseInput.equals("Choisir une réponse prédéfinie")){
            compteur++;
            showError("Reponse Invalide",comboBoxErrorLabel);
        }
        else{
            comboBoxErrorLabel.setOpacity(0);
        }
        if(compteur ==0) {
            Reclamation reclamation = UserSessionManager.getInstance().getReclamation();
            User user = userService.recherparid(reclamation.getUser_id());
            System.out.println("User");
            System.out.println(user);
            Reponse reponse = new Reponse(reponseInput, reclamation.getId());
            serviceReponse.ajouterReponse(reponse);
            serviceReclamation.repondreReclamation("Résolu", reclamation.getId());
            EmailSender.sendEmail(
                    "alpha2025group@gmail.com",
                    "hrrjbkauhdctymvk",
                    user.getEmail(),
                    "Reponse a reclamation",
                    reponseInput
            );

            this.pageReclamation();
        }
    }




    public void pagedashboard() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("user.fxml", currentStage);
    }
    public void pageupdate() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("updateback.fxml", currentStage);
    }

    public void pageAssociation() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageBack.fxml", currentStage);
    }
    public void pageReclamation() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Reclamation/reclamationBack.fxml", currentStage);
    }
    public void pageuser() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/backback.fxml", currentStage);
    }
    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }
}
