package tn.chouflifilm.controller.user;

import com.example.demo.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.UserSessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class frontController {
    private userService userService = new userService();

    @FXML
    private ImageView imageView;
@FXML
private TextField nomTextField;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("hello");
User user= userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());
        String imageFromDb = user.getimage();

        Image image = new Image(imageFromDb);


        imageView.setImage(image);

        // Crée un clip circulaire pour l'image
        Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
        imageView.setClip(clipCircle);
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
