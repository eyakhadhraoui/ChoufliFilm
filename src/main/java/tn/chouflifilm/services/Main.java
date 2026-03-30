package tn.chouflifilm.services;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// Pour les notifications système
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {



            showSystemNotification("Notification professionnelle",
                    "Votre action a été traitée avec succès.",
                    MessageType.INFO);


}

public void sendNotification() {
    showSystemNotification("Notification professionnelle",
            "Votre action a été traitée avec succès.",
            MessageType.INFO);

}

    private void showSystemNotification(String title, String message, MessageType type) {
        try {
            // Vérifier si le système supporte les notifications système
            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray n'est pas supporté");
                return;
            }

            // Obtenir l'instance du SystemTray
            SystemTray tray = SystemTray.getSystemTray();

            // Charger l'image en utilisant la même méthode que pour l'icône de la fenêtre
            InputStream imageStream = getClass().getResourceAsStream("/images/logo.png");
            BufferedImage bufferedImage = ImageIO.read(imageStream);
            java.awt.Image awtImage = bufferedImage;

            // Créer un TrayIcon
            TrayIcon trayIcon = new TrayIcon(awtImage, "Choufli Film");
            trayIcon.setImageAutoSize(true);

            // Ajouter l'icône au System Tray
            try {
                tray.add(trayIcon);
            } catch (Exception ex) {
                // Si l'icône existe déjà dans le tray, on continue simplement
                System.out.println("L'icône existe déjà dans le tray ou erreur: " + ex.getMessage());
            }

            // Afficher la notification
            trayIcon.displayMessage(title, message, type);

        } catch (Exception ex) {
            System.err.println("Erreur lors de l'envoi de la notification: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}