package tn.chouflifilm.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class editFrontController {
    private tn.chouflifilm.services.userService userService = new userService();
    @FXML
    private Label imagePathLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
@FXML
private TextField emailTextField;

@FXML
private TextField phoneTextField;
@FXML
private TextField localisationTextField;
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

        User user = userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());
        nomTextField.setText(user.getNom());
        prenomTextField.setText(user.getPrenom());
        emailTextField.setText(user.getEmail());
phoneTextField.setText(user.getNum_telephone()+"");
localisationTextField.setText(user.getLocalisation());
String imageFromDb = user.getimage();
        Image image1 = new Image(imageFromDb);
        imageView.setImage(image1);
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
        String nomInput = nomTextField.getText();
        String prenomInput = prenomTextField.getText();
        String emailInput = emailTextField.getText();

        String localisationInput = localisationTextField.getText();
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
        String phoneInput1 = phoneTextField.getText();

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
            int numTelephoneInput = Integer.parseInt(phoneTextField.getText());
            userService.modifier(nomInput, prenomInput, emailInput, localisationInput, numTelephoneInput, imagePathInput, id);

            this.pageFront();
        }
    }


    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }

    public void pageFront() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/user/front.fxml", currentStage);

    }
    public void pageReclamation() throws IOException {

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/Reclamation/affichageReclamationFront.fxml", currentStage);
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
