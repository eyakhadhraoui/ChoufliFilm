package tn.chouflifilm.controller.user;

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
import org.mindrot.jbcrypt.BCrypt;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class testcontroller {
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
    private DatePicker dateNaissance;
    @FXML
    private Circle circle11;

    @FXML
    private Circle circle12;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField email;

    @FXML
    private TextField numTelephone;



    @FXML
    private TextField localisation;

    @FXML
    private Button imageChooserButton;

    @FXML
    private Label imagePathLabel;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button signupButton;

    @FXML
    private Hyperlink pageConnexion;

    @FXML
    private Label errorMessageLabel;

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
    private Label passwordError;
    @FXML
    private Label confirmPasswordError;
    @FXML
    private Label emailError;

    @FXML
    private RadioButton adminRole;
    @FXML
    private RadioButton userRole;

    private ToggleGroup roleGroup;

    private tn.chouflifilm.services.userService userService = new userService();
    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Bouton S'inscrire cliqué !");
    }


    @FXML
    public void initialize() {
//        roleGroup = new ToggleGroup();
//        adminRole.setToggleGroup(roleGroup);
//        userRole.setToggleGroup(roleGroup);

        animateBackgroundCircles();
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

        // Circle 12 - Center right side
        TranslateTransition transition12 = new TranslateTransition(Duration.seconds(4), circle12);
        transition12.setFromX(950);
        transition12.setFromY(0);
        transition12.setToX(1050);
        transition12.setToY(75);
        transition12.setAutoReverse(true);
        transition12.setCycleCount(TranslateTransition.INDEFINITE);
        transition12.play();
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
            imagePathLabel.setText(selectedFile.getAbsolutePath()); // Affiche le chemin de l'image
        }
    }

    @FXML
    public void signin(ActionEvent event) throws IOException, SQLException {
        int compteur =0;

        String nomInput = nom.getText();



        if( nomInput==null || nomInput.equals("")  ||nomInput.length()<3 ) {
            compteur ++;
            showError("nomInvalide",errornom);
            System.out.println("nom invalide");

        }
        else{
            errornom.setOpacity(0);
        }


        String prenomInput = prenom.getText();
        if( prenomInput==null || prenomInput.equals("")  ||prenomInput.length()<3 ) {
            compteur ++;
            showError("PrenomInvalide",prenomError);
            System.out.println("Prenom invalide");

        }
        else{
            prenomError.setOpacity(0);
        }

        String phoneInput1 = numTelephone.getText();

        if (!phoneInput1.matches("[0-9]{8}")) {
            compteur ++;
            showError("Numéro invalide",numTelephoneError);
            System.out.println("numTelephone invalide");
        }
        else{
            numTelephoneError.setOpacity(0);
        }
        String emailInput = email.getText();

        User user12 = userService.recherparemail(emailInput);



        if (emailInput.isEmpty() || !emailInput.matches("^[\\w.-]+@(gmail\\.com|esprit\\.tn)$") || user12 != null) {
            compteur ++;
            showError("Email invalide",emailError);
            System.out.println("Email valide");
        } else {
            System.out.println("Email invalide");
            emailError.setOpacity(0);
        }

        String passwordInput = password.getText();
        if( passwordInput==null || passwordInput.equals("")  ||passwordInput.length()<3 ) {
            compteur ++;
            showError("Password Invalide",passwordError);
            System.out.println("Password invalide");

        }
        else{
            passwordError.setOpacity(0);
        }
        String confirmPasswordInput = confirmPassword.getText();
        if( confirmPasswordInput==null || confirmPasswordInput.equals("")  || confirmPasswordInput.length()<3 ) {
            compteur ++;
            showError("Confirm Password Invalide",confirmPasswordError);
            System.out.println("Confirm Password invalide");

        }
        else{
            System.out.println("Confirm Password valide yes yes yes");
            confirmPasswordError.setOpacity(0);
        }


        String localisationInput = localisation.getText();
        if (localisationInput.length()<3 || localisationInput.equals("") ) {
            compteur ++;
            showError("Localisation invalide",localisationError);
            System.out.println("Localisation invalide");
        }
        else{
            localisationError.setOpacity(0);
            System.out.println("Localisation valide");
        }


        Date dateNaissanceInput = null;

        if (dateNaissance.getValue() != null) {
            LocalDate selectedDate = dateNaissance.getValue();
            LocalDate today = LocalDate.now();
            int age = Period.between(selectedDate, today).getYears();

            System.out.println("Date sélectionnée : " + selectedDate);
            System.out.println("Âge calculé : " + age);

            if (age < 10) {
                showError("L'âge doit être au moins de 10 ans", dateNaissanceError);
                dateNaissanceError.setOpacity(1); // Afficher visuellement l’erreur si pas déjà géré
                compteur++;
            } else {
                dateNaissanceInput = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                System.out.println("Date de naissance validée : " + dateNaissanceInput);
                dateNaissanceError.setOpacity(0); // Cacher l’erreur
            }
        } else {
            System.out.println("Aucune date sélectionnée.");
            showError("Date invalide", dateNaissanceError);
            dateNaissanceError.setOpacity(1);
            compteur++;
        }
        String imagePathInput = imagePathLabel.getText();

        Path destinationFolder = Paths.get("src/main/resources/images");
        Files.createDirectories(destinationFolder);

        Path destinationPath;

        if (imagePathInput == null || imagePathInput.trim().isEmpty() || !(new File(imagePathInput).exists())) {
            // Utiliser l'image par défaut
            imagePathInput="C:\\Users\\21694\\Downloads\\user.jpg";
        } else {
            Path sourcePath = Paths.get(imagePathInput);

            // Générer un nom de fichier unique
            String fileName = sourcePath.getFileName().toString();
            destinationPath = destinationFolder.resolve(fileName);

            int counter = 1;
            while (Files.exists(destinationPath)) {
                String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                destinationPath = destinationFolder.resolve(fileNameWithoutExtension + "_" + counter + fileExtension);
                counter++;
            }

            // Copier l'image sélectionnée vers le dossier des ressources
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image copiée : " + destinationPath);
        }

        String hashedPassword = BCrypt.hashpw(passwordInput, BCrypt.gensalt());

        if(compteur ==0) {
            int phoneInput = Integer.parseInt(numTelephone.getText());
            User user = new User(nomInput, prenomInput, emailInput, phoneInput, dateNaissanceInput, localisationInput, imagePathInput, hashedPassword, confirmPasswordInput);
            try {
                // Appeler la méthode authenticateUser pour vérifier les identifiants
                userService.ajouterUser(user);

                this.pagelogin();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                showError("Une erreur est survenue lors du chargement de la fenêtre suivante.", errorMessageLabel);
            }
        }
        else{
            return ;
        }
    }
    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }


    public void pagelogin() throws IOException {
        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
}
