package tn.chouflifilm.controller.user;

import com.example.demo.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.HelloApplication;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.text.ParseException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import tn.chouflifilm.tools.UserSessionManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
public class editcontroller {




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


    @FXML private TextField nom;
    @FXML private TextField prenom;
    @FXML private TextField email;
    @FXML private TextField numTelephone;
    @FXML private DatePicker dateNaissance;
    @FXML private TextField localisation;

    @FXML
    private ImageView imageView;

     @FXML
     private Label nomlabel;

    @FXML
    private Label prenomlabel;

    @FXML
    private Label emaillabel;
    @FXML
    private Label tellabel;
    @FXML
    private Label localisationlabel;

    @FXML
    private Label imagePathLabel;

    private tn.chouflifilm.services.userService userService = new userService();


    @FXML
    private Label localisationErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label nomErrorLabel;
    @FXML
    private Label prenomErrorLabel;




    @FXML
    public void initialize() throws SQLException {

        User user= userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());
System.out.println(user);


        nomlabel.setText(user.getNom());
        prenomlabel.setText(user.getPrenom());
        emaillabel.setText(user.getEmail());
        localisationlabel.setText(user.getLocalisation());
        tellabel.setText(user.getNum_telephone()+"");


if(imagePathLabel.getText().equals("Aucune image sélectionnée") || imagePathLabel.getText().equals(UserSessionManager.getInstance().getCurrentUser().getimage()) ) {

    if (UserSessionManager.getInstance().isLoggedIn()) {
        String imagePath = UserSessionManager.getInstance().getCurrentUser().getimage();
        Image image = new Image(imagePath);


        imageView.setImage(image);

        // Crée un clip circulaire pour l'image
        Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
        imageView.setClip(clipCircle);
    }
}



        animateBackgroundCircles();

        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        email.setText(user.getEmail());
        String numTelephoneStr = String.valueOf(user.getNum_telephone());
        numTelephone.setText(numTelephoneStr);

        localisation.setText(user.getLocalisation());

    }

    @FXML
    public void chooseImage() {

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

            // Crée un clip circulaire pour l'image
            Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
            imageView.setClip(clipCircle);
        }
    }

    public void edit() throws SQLException, IOException {
        int compteur =0;
        int id = UserSessionManager.getInstance().getCurrentUser().getId();
        System.out.println(id);
        String nomInput = nom.getText();
        String prenomInput = prenom.getText();
        String emailInput = email.getText();

        String localisationInput = localisation.getText();
        String imagePathInput = imagePathLabel.getText();
        if (nomInput.equals("") || nomInput.length() <=3) {
            compteur ++;
            showError("nomInvalide",nomErrorLabel);
            System.out.println("nom invalide");
        }
        else{
            nomErrorLabel.setOpacity(0);
        }
        if (prenomInput.equals("") || prenomInput.length() <=3) {
            compteur ++;
            showError("prenomInvalide",prenomErrorLabel);
            System.out.println("prenom invalide");
        }
        else {
            prenomErrorLabel.setOpacity(0);
        }
        if (emailInput.isEmpty() || !emailInput.matches("^[\\w.-]+@(gmail\\.com|esprit\\.tn)$")) {
            showError("Email invalide",emailErrorLabel);
            System.out.println("Email valide");
            compteur++;
        } else {
            System.out.println("Email invalide");
            emailErrorLabel.setOpacity(0);
        }
        if (localisationInput.equals("") || localisationInput.length() <=3) {
            compteur ++;
            showError("localisationInvalide",localisationErrorLabel);
        }
        else {
            localisationErrorLabel.setOpacity(0);
        }
        String phoneInput1 = numTelephone.getText();

        if (!phoneInput1.matches("[0-9]{8}")) {
            showError("Numéro invalide",phoneErrorLabel);
            compteur ++;
            System.out.println("numTelephone invalide");
        }
        else{
            phoneErrorLabel.setOpacity(0);
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

        if (compteur==0) {
            int numTelephoneInput = Integer.parseInt(numTelephone.getText());
            userService.modifier(nomInput, prenomInput, emailInput, localisationInput, numTelephoneInput, imagePathInput, id);

            this.pageuser();
        }
    }


    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }
    public void cancelaffichage(){
        nom.setText("");
        prenom.setText("");
        email.setText("");
        localisation.setText("");
       numTelephone.setText("");
    }

    private void animateBackgroundCircles() {
        // Left side circles (x from 0 to 400)

        // Circle 1 - Top left
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(3), circle1);
        transition1.setFromX(0);
        transition1.setFromY(0);
        transition1.setToX(100);
        transition1.setToY(-50);
        transition1.setAutoReverse(true);
        transition1.setCycleCount(TranslateTransition.INDEFINITE);
        transition1.play();

        // Circle 2 - Bottom left
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(4), circle2);
        transition2.setFromX(0);
        transition2.setFromY(0);
        transition2.setToX(-100);
        transition2.setToY(50);
        transition2.setAutoReverse(true);
        transition2.setCycleCount(TranslateTransition.INDEFINITE);
        transition2.play();

        // Circle 3 - Middle left
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(3), circle3);
        transition3.setFromX(200);
        transition3.setFromY(0);
        transition3.setToX(300);
        transition3.setToY(100);
        transition3.setAutoReverse(true);
        transition3.setCycleCount(TranslateTransition.INDEFINITE);
        transition3.play();

        // Circle 4 - Upper middle left
        TranslateTransition transition4 = new TranslateTransition(Duration.seconds(4), circle4);
        transition4.setFromX(300);
        transition4.setFromY(0);
        transition4.setToX(400);
        transition4.setToY(-75);
        transition4.setAutoReverse(true);
        transition4.setCycleCount(TranslateTransition.INDEFINITE);
        transition4.play();

        // Circle 5 - Far left side
        TranslateTransition transition5 = new TranslateTransition(Duration.seconds(3), circle5);
        transition5.setFromX(-100);
        transition5.setFromY(0);
        transition5.setToX(0);
        transition5.setToY(50);
        transition5.setAutoReverse(true);
        transition5.setCycleCount(TranslateTransition.INDEFINITE);
        transition5.play();

        // Circle 6 - Center left side
        TranslateTransition transition6 = new TranslateTransition(Duration.seconds(4), circle6);
        transition6.setFromX(150);
        transition6.setFromY(0);
        transition6.setToX(200);
        transition6.setToY(-50);
        transition6.setAutoReverse(true);
        transition6.setCycleCount(TranslateTransition.INDEFINITE);
        transition6.play();

        // Right side circles (x from 600 to 950)

        // Circle 7 - Top right
        TranslateTransition transition7 = new TranslateTransition(Duration.seconds(3), circle7);
        transition7.setFromX(600);
        transition7.setFromY(0);
        transition7.setToX(700);
        transition7.setToY(-50);
        transition7.setAutoReverse(true);
        transition7.setCycleCount(TranslateTransition.INDEFINITE);
        transition7.play();

        // Circle 8 - Bottom right
        TranslateTransition transition8 = new TranslateTransition(Duration.seconds(4), circle8);
        transition8.setFromX(700);
        transition8.setFromY(0);
        transition8.setToX(800);
        transition8.setToY(100);
        transition8.setAutoReverse(true);
        transition8.setCycleCount(TranslateTransition.INDEFINITE);
        transition8.play();

        // Circle 9 - Middle right
        TranslateTransition transition9 = new TranslateTransition(Duration.seconds(3), circle9);
        transition9.setFromX(800);
        transition9.setFromY(0);
        transition9.setToX(900);
        transition9.setToY(-75);
        transition9.setAutoReverse(true);
        transition9.setCycleCount(TranslateTransition.INDEFINITE);
        transition9.play();

        // Circle 10 - Upper middle right
        TranslateTransition transition10 = new TranslateTransition(Duration.seconds(4), circle10);
        transition10.setFromX(900);
        transition10.setFromY(0);
        transition10.setToX(950);
        transition10.setToY(50);
        transition10.setAutoReverse(true);
        transition10.setCycleCount(TranslateTransition.INDEFINITE);
        transition10.play();

        // Circle 11 - Far right side
        TranslateTransition transition11 = new TranslateTransition(Duration.seconds(3), circle11);
        transition11.setFromX(1000);
        transition11.setFromY(0);
        transition11.setToX(1100);
        transition11.setToY(-50);
        transition11.setAutoReverse(true);
        transition11.setCycleCount(TranslateTransition.INDEFINITE);
        transition11.play();


        TranslateTransition transition12 = new TranslateTransition(Duration.seconds(4), circle12);
        transition12.setFromX(950);
        transition12.setFromY(0);
        transition12.setToX(1050);
        transition12.setToY(75);
        transition12.setAutoReverse(true);
        transition12.setCycleCount(TranslateTransition.INDEFINITE);
        transition12.play();
    }

    public void pageuser() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("backback.fxml", currentStage);
    }
    public void dashboard() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("user.fxml", currentStage);
    }


    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
    public void pageAssociation() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageBack.fxml", currentStage);
    }


}
