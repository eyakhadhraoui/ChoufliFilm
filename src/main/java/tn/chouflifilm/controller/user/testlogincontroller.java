package tn.chouflifilm.controller.user;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import tn.chouflifilm.tools.UserSessionManager;

public class testlogincontroller {
    @FXML
    private ImageView imageView;

    public void initialize() {
        // Récupère l'image de l'utilisateur
        String imagePath = UserSessionManager.getInstance().getCurrentUser().getimage();
        Image image = new Image(imagePath);

        // Applique l'image à l'ImageView
        imageView.setImage(image);

        // Crée un clip circulaire pour l'image
        Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
        imageView.setClip(clipCircle);
    }

}
