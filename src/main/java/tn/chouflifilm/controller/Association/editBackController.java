package tn.chouflifilm.controller.Association;

import com.example.demo.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class editBackController {

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
    private ImageView imageView;
    @FXML
    private Label imagePathLabel;
    @FXML private TextField nom;
    @FXML private TextField adresse;
    @FXML private TextField email;
    @FXML private TextField numTelephone;
     @FXML private TextArea description;
    @FXML private  TextField idField;
    @FXML
    private Label errornom;
    @FXML
    private Label prenomError;
    @FXML
    private Label numTelephoneError;
    @FXML
    private Label dateNaissanceError;
    @FXML
    private Label localisationError;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    private tn.chouflifilm.services.Association.associationService associationService = new associationService();



    private Association association;

    public void setAssociation(Association association) {

        this.association = association;

        Platform.runLater(() -> {

            idField.setText(String.valueOf(association.getId()));
            nom.setText(association.getNom());
            email.setText(association.getMail_association());
            adresse.setText(association.getAdresse());
            numTelephone.setText(association.getNum_tel()+"");
            description.setText(association.getDescription());

            String imagePath = association.getImage();
            Image image = new Image(imagePath);
            imageView.setImage(image);

            Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
            imageView.setClip(clipCircle);

        });

    }



    public void edit() throws SQLException, IOException {
int compteur =0;
        String nomInput = nom.getText();
        String adresseInput = adresse.getText();
        String emailInput = email.getText();

        String descriptionInput = description.getText();
        String imagePathInput = imagePathLabel.getText();
        if( nomInput==null || nomInput.equals("")  ||nomInput.length()<3 ) {
            showError("nomInvalide",errornom);
            System.out.println("nom invalide");
            compteur++;
        }
        else{
            errornom.setOpacity(0);
        }
        if (descriptionInput.equals("") || descriptionInput.length()<3 || descriptionInput.length()>255 || descriptionInput==null) {
            showError("Description Invalide",passwordError);
            System.out.println("Description invalide");
            compteur++;
        }
        else{
passwordError.setOpacity(0);
        }
        if(adresseInput==null || adresseInput.equals("")  ||adresseInput.length()<3 ) {
            showError("Adresse Invalide",prenomError);
            System.out.println("Adresse  invalide");
            compteur ++;
        }
        else{
            prenomError.setOpacity(0);
        }
        if (emailInput.isEmpty() || !emailInput.matches("^[\\w.-]+@(gmail\\.com|esprit\\.tn)$")) {
            showError("Email invalide",numTelephoneError);
            System.out.println("Email valide");
            compteur++;
        } else {
            System.out.println("Email invalide");
            numTelephoneError.setOpacity(0);
        }

        String phoneInput1 = numTelephone.getText();

        if (!phoneInput1.matches("[0-9]{8}")) {
            showError("Numéro invalide",emailError);
            compteur ++;
            System.out.println("numTelephone invalide");
        }
        else{
            emailError.setOpacity(0);
        }




        System.out.println(imagePathInput);
        if (!imagePathInput.equals("Aucune image sélectionnée")) {
            Path sourcePath = Paths.get(imagePathInput);

            // Créer le dossier ressources/images s'il n'existe pas
            Path destinationFolder = Paths.get("src/main/resources/images");
            Files.createDirectories(destinationFolder);

            // Générer un nom de fichier unique pour éviter les écrasements
            String fileName = sourcePath.getFileName().toString();

            Path destinationPath = destinationFolder.resolve(fileName);

            // Gérer les fichiers en double
            int counter = 1;
            while (Files.exists(destinationPath)) {
                String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                destinationPath = destinationFolder.resolve(fileNameWithoutExtension + "_" + counter + fileExtension);
                counter++;
            }

            // Copier le fichier (avec remplacement si nécessaire)
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Optionnel : supprimer le fichier original
            // Files.delete(sourcePath);

            System.out.println("Image déplacée avec succès vers : " + destinationPath);
        }
        else {
            System.out.println("d5al");
            imagePathInput= UserSessionManager.getInstance().getCurrentUser().getimage();
        }
        if(compteur==0) {
            System.out.println("Compteur est vide");
            int numTelephoneInput = Integer.parseInt(numTelephone.getText());
            int idInput = Integer.parseInt(idField.getText());
            associationService.modifierAssociation(nomInput, emailInput, numTelephoneInput, imagePathInput, descriptionInput, idInput);
            this.pageAssociation();

        }



    }






    @FXML
    public void chooseImage() {
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************");

        System.out.println(idField.getText());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une Image de Profil");

        // Filtrer uniquement les fichiers image
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePathLabel.setText(selectedFile.getAbsolutePath());
            String imagePath = imagePathLabel.getText();
            Image image = new Image(imagePath);


            imageView.setImage(image);


            Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
            imageView.setClip(clipCircle);
        }
    }








    public void pageuser() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("backback.fxml", currentStage);
    }
    public void dashboard() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("user.fxml", currentStage);
    }
    public void pageAssociation() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageBack.fxml", currentStage);
    }
    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }


}
