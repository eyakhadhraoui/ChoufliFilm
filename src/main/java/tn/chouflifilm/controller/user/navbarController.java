package tn.chouflifilm.controller.user;

import com.example.demo.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class navbarController {

    public static void changeScene(String file, Stage currentStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(file));
        Parent newView = fxmlLoader.load();


        currentStage.getScene().setRoot(newView);
    }
}
