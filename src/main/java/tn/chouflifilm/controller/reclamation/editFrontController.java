package tn.chouflifilm.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.chouflifilm.controller.user.navbarController;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
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
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button imageChooserButton;
    @FXML
    private Label imagePathLabel;
    @FXML
    private Label typeErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label imageErrorLabel;
    @FXML
    private ImageView imageView;

    private userService userService = new userService();
    private serviceReclamation serviceReclamation = new serviceReclamation();

    @FXML
    public void initialize() throws SQLException {
        typeComboBox.setValue(UserSessionManager.getInstance().getReclamation().getType());
        descriptionTextArea.setText(UserSessionManager.getInstance().getReclamation().getDescription());
        imagePathLabel.setText(UserSessionManager.getInstance().getReclamation().getImage());

        User user = userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());
        String imageFromDb = user.getimage();
        Image image1 = new Image(imageFromDb);
        imageView.setImage(image1);
    }

    @FXML
    public void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une Image de Profil");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    public void addReclamation() throws IOException, SQLException {
        int compteur = 0;
        String description = descriptionTextArea.getText();
        String type = typeComboBox.getValue();
        String imagePathInput = imagePathLabel.getText();
        String imago = UserSessionManager.getInstance().getReclamation().getImage(); // garder l'ancienne image

        // Validation des champs
        if (description.length() <= 3) {
            compteur++;
            showError("Description Invalide", descriptionErrorLabel);
        } else {
            descriptionErrorLabel.setOpacity(0);
        }

        if (type == null || type.equals("Sélectionnez le type de réclamation")) {
            compteur++;
            showError("Type Invalide", typeErrorLabel);
        } else {
            typeErrorLabel.setOpacity(0);
        }

        // Si un nouveau fichier image a été sélectionné
        if (imagePathInput != null && !imagePathInput.equals(imago) && !imagePathInput.isEmpty()) {
            Path sourcePath = Paths.get(imagePathInput);
            Path destinationFolder = Paths.get("src/main/resources/images");
            Files.createDirectories(destinationFolder);

            String fileName = sourcePath.getFileName().toString();
            Path destinationPath = destinationFolder.resolve(fileName);

            int counter = 1;
            while (Files.exists(destinationPath)) {
                String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                destinationPath = destinationFolder.resolve(fileNameWithoutExtension + "_" + counter + fileExtension);
                imago = "/images/" + fileNameWithoutExtension + "_" + counter + fileExtension;
                counter++;
            }

            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image déplacée avec succès vers : " + destinationPath);
        }
        imageErrorLabel.setOpacity(0); // Pas d'erreur, image optionnelle

        // Si tout est valide, procéder à la modification
        if (compteur == 0) {
            serviceReclamation.modifierReclamation(description, imago, type,
                    UserSessionManager.getInstance().getReclamation().getId());
            this.pageReclamation();
        }
    }

    public void pageReclamation() throws IOException {
        Stage currentStage = (Stage) descriptionTextArea.getScene().getWindow();
        navbarController.changeScene("/Reclamation/affichageReclamationFront.fxml", currentStage);
    }

    private void showError(String message, Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }

    public void pageFront() throws IOException {
        Stage currentStage = (Stage) descriptionTextArea.getScene().getWindow();
        navbarController.changeScene("/user/front.fxml", currentStage);
    }

    public void pageupdateFront() throws IOException {
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("/user/editFront.fxml", currentStage);
    }

    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
}
