package tn.chouflifilm.controller.user;
import com.example.demo.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.services.GoogleAuthExample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import tn.chouflifilm.tools.UserSessionManager;

public class signincontroller {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

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
    private TextField id;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField age;

    @FXML
    private Label errorMessageLabel; // Le Label où afficher l'erreur

    private userService userService = new userService();

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> prenomColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Integer> numeroColumn;
    @FXML
    private TableColumn<User, Void> supprimerColumn;
    @FXML
    private TableColumn<User, Void> updatecolumn;
    @FXML
    private ImageView imageView;





    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de l'objet User
        if(UserSessionManager.getInstance().isLoggedIn()){
        String imagePath = UserSessionManager.getInstance().getCurrentUser().getimage();
        Image image = new Image(imagePath);

        // Applique l'image à l'ImageView
        imageView.setImage(image);

        // Crée un clip circulaire pour l'image
        Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
        imageView.setClip(clipCircle);}     // Si vous avez une animation pour les cercles en arrière-plan, vous pouvez l'ajouter ici
        animateBackgroundCircles();
    }

    private void supprimerUtilisateur(User user) {
        try {
            // Supprimer l'utilisateur de la base de données via le service
            userService.supprimerUser(user);

            // Si la suppression réussit, retirer l'utilisateur de la table affichée
            tableView.getItems().remove(user);
        } catch (SQLException e) {
            e.printStackTrace();  // Gérer l'exception et l'afficher dans la console
            // Vous pouvez également afficher un message d'erreur à l'utilisateur si la suppression échoue
        }
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
    public void signin(ActionEvent event) {
        String emailInput = email.getText();
        String passwordInput = password.getText();

        try {
            // Appeler la méthode authenticateUser pour vérifier les identifiants
            User user = userService.Authentification(emailInput, passwordInput);
    System.out.println(user);

            if (user != null) {
                if (user.getBanned() == 1) {
                    showError("utilisateur banni");
                    return;
                }
                if (user.getDeleted() == 1) {
                    showError("utilisateur Supprimé");
                    return;
                }
                UserSessionManager.getInstance().setCurrentUser(user);

                String file = "user.fxml";
                System.out.println("Utilisateur Trouvé");
                String result1 = Arrays.toString(user.getRoles()); // Cela va donner une chaîne comme "[ROLE_USER]"
                String formattedResult1 = "[" + result1.substring(1, result1.length() - 1) + "]";

                String[] roles = new String[]{"ROLE_USER"};
                String expected = "[[\"" + String.join("\", \"", roles) + "\"]]";


                String[] roles1 = new String[]{"ROLE_ADMIN"};
                String expected1 = "[[\"" + String.join("\", \"", roles) + "\"]]";

                if (formattedResult1.equals(expected)) {
                    System.out.println("d5al");
                    file = "/user/front.fxml";
                }
                else {
                    file = "user.fxml";
                }
                Stage currentStage = (Stage) email.getScene().getWindow();

                // Chargement de la nouvelle fenêtre
            navbarController.changeScene(file,currentStage);


            } else {
                System.out.println("Utilisateur non trouvé");
                showError("Email ou mot de passe incorrect.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Impossible de se connecter à la base de données. Veuillez réessayer.");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Une erreur est survenue lors du chargement de la fenêtre suivante.");
        }
    }


    private void showError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setOpacity(1);
    }
    public void inscription(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inscription.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        Stage stage = new Stage();
        stage.setTitle("Inscription Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void adduserinscription(ActionEvent event) throws IOException, SQLException {
        User us = new User("imed","garram","eya@gmail1.com",24705158,new Date(),"tunis","inconnu.jpg","","");
        userService.ajouterUser(us);
            User currentUser = UserSessionManager.getInstance().getCurrentUser();
            System.out.println(currentUser.getimage());

    }

    public void pageupdate(ActionEvent event) throws IOException {
        // Charger la nouvelle scène
    this.pageupdate();
    }

    public void authgoolee() {

GoogleAuthExample GoogleAuthExample = new GoogleAuthExample();
        Stage stage = new Stage();
GoogleAuthExample.start(stage);
    }

    public void pageinscription() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("inscription.fxml", currentStage);
    }


    public void pageupdate() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("updateback.fxml", currentStage);
    }
    public void pagereset() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("mail.fxml", currentStage);
    }

}
