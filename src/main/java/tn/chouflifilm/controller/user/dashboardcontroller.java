package tn.chouflifilm.controller.user;

import com.example.demo.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.mindrot.jbcrypt.BCrypt;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.entities.ressource;
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
import java.time.LocalDate;
import java.time.Period;
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

public class dashboardcontroller {
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
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField email;

    @FXML
    private TextField numTelephone;

    @FXML
    private DatePicker dateNaissance;

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
    private TextField id;

    @FXML
    private TextField searchField;


    @FXML
    private TextField age;

    @FXML
    private TableColumn<User, String> rolsColumn;

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
    private TableColumn<User, String> bannedColumn;


    @FXML
    private TableColumn<User, Void> actionsColumn;

    @FXML
    private ImageView imageView;


    @FXML
    private RadioButton adminRole;
    @FXML
    private RadioButton userRole;

    private ToggleGroup roleGroup;
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
    private Label rolesError;
    @FXML
    private TableColumn<User, Void> imageUserColumn;
    @FXML
    public void initialize() throws SQLException {

        roleGroup = new ToggleGroup();
        adminRole.setToggleGroup(roleGroup);
        userRole.setToggleGroup(roleGroup);
        imageUserColumn.setCellFactory(column -> new TableCell<User, Void>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitHeight(75);
                imageView.setFitWidth(60);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    User res = getTableRow().getItem();
                    System.out.println(res);
                    String imagePath = res.getimage();

                    try {
                        Image image;

                        if (imagePath.startsWith("http") || imagePath.startsWith("file:/")) {
                            image = new Image(imagePath); // pour les URLs ou chemins déjà convertis
                        } else {
                            image = new Image(new File(imagePath).toURI().toString()); // depuis le disque
                        }

                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
                        setGraphic(null);
                    }
                }
            }

        });

        if(UserSessionManager.getInstance().isLoggedIn()){
            User user= userService.recherparid(UserSessionManager.getInstance().getCurrentUser().getId());

            String imagePath = user.getimage();
            Image image = new Image(imagePath);

            // Applique l'image à l'ImageView
            imageView.setImage(image);

            // Crée un clip circulaire pour l'image
            Circle clipCircle = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
            imageView.setClip(clipCircle);}
        else{
            System.out.println("rahou mahouch connecte");

        }


        imagePathLabel.setText("Aucune image Séléectionné");
        imagePathLabel.setMaxWidth(200);
        imagePathLabel.setEllipsisString("...");

        animateBackgroundCircles();
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));


        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));

        bannedColumn.setPrefWidth(100.0);

        bannedColumn.setCellValueFactory(cellData -> {
            int bannedValue = cellData.getValue().getBanned();
            String displayText = (bannedValue == 1) ? "Banned" : "Not Banned";
            return new SimpleStringProperty(displayText);
        });


        rolsColumn.setPrefWidth(160.0);

        rolsColumn.setCellValueFactory(cellData -> {
            String[] rolesArray = cellData.getValue().getRoles();
            if (rolesArray == null || rolesArray.length == 0) {
                return new SimpleStringProperty("Inconnu");
            }

            String rawRole = rolesArray[0].toUpperCase();

            String displayText;
            if (rawRole.contains("ROLE_USER")) {
                displayText = "USER";
            } else if (rawRole.contains("ROLE_ADMIN")) {
                displayText = "ADMIN" ;
            } else {
                displayText = "Inconnu";
            }

            return new SimpleStringProperty(displayText);
        });

        rolsColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item) {
                        case "USER":
                            setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                            break;
                        case "ADMIN":
                            setStyle("-fx-text-fill: goldenrod; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: gray;");
                            break;
                    }
                }
            }
        });


        bannedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equals("Banned")) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    }
                }
            }
        });




        nomColumn.setSortable(true);
        prenomColumn.setSortable(true);

        nomColumn.setOnEditStart(event -> {
            ObservableList<User> sortedList = tableView.getItems().stream()
                    .sorted(Comparator.comparing(User::getNom))
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

            tableView.setItems(sortedList);
        });
        prenomColumn.setOnEditStart(event -> {
            ObservableList<User> sortedList = tableView.getItems().stream()
                    .sorted(Comparator.comparing(User::getPrenom))
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

            tableView.setItems(sortedList);
        });

        try {
            List<User> users = userService.afficherdetailsuser();
            ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
            tableView.setItems(observableUsers);
        } catch (SQLException e) {
            e.printStackTrace();

        }







      actionsColumn.setCellFactory(param -> new TableCell<User, Void>() {
          private final Button updateButton = new Button("\uD83D\uDD04");
          private final Button supprimerButton = new Button("❌");
          private final Button BannerButton = new Button("\uD83D\uDEAB");




          {


              updateButton.setOnAction(event -> {
                  User user = getTableView().getItems().get(getIndex());
                  System.out.println(user);
                  System.out.println(user.getEmail());
                  unbanneruser(user);
                  List<User> users = null;
                  try {
                      users = userService.afficherdetailsuser();
                  } catch (SQLException e) {
                      throw new RuntimeException(e);
                  }
                  ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
                  tableView.setItems(observableUsers);


              });


              BannerButton.setOnAction(event -> {
                  User user = getTableView().getItems().get(getIndex());
                  System.out.println(user);
                  System.out.println(user.getEmail());
                  banneruser(user);
                  List<User> users = null;
                  try {
                      users = userService.afficherdetailsuser();
                  } catch (SQLException e) {
                      throw new RuntimeException(e);
                  }
                  ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
                  tableView.setItems(observableUsers);


              });



                      supprimerButton.setOnAction(event -> {
                          User user = getTableView().getItems().get(getIndex());
                          System.out.println(user.getEmail());

                          supprimerUtilisateur(user);
                      });
                  }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);  // Aucune cellule à afficher si vide
                } else {
                    updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
                    supprimerButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");

                    BannerButton.setStyle("-fx-background-color: #FF4500; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
                    String[] roles1 = new String[]{"ROLE_ADMIN"};
                    String expected1 = "[[\"" + String.join("\", \"", roles1) + "\"]]";
                    String result1 = Arrays.toString(getTableView().getItems().get(getIndex()).getRoles());
                    String formattedResult1 = "[" + result1.substring(1, result1.length() - 1) + "]";
                    if (formattedResult1.equals(expected1)) {
                         final HBox pane1 =  new HBox(10,updateButton, BannerButton);
                        setGraphic(pane1);
                    }
                    else{
                        final HBox pane = new HBox(10,updateButton, supprimerButton,BannerButton);
                        setGraphic(pane);
                    }
                }
            }
        });

        // Charger les utilisateurs dans la table
        try {
            List<User> users = userService.afficherdetailsuser();
            ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
            tableView.setItems(observableUsers);
        } catch (SQLException e) {
            e.printStackTrace();  // Gérer l'exception et l'afficher dans la console
            // Vous pouvez ajouter une gestion d'erreur ici, comme afficher un message d'erreur à l'utilisateur
        }



    }
    private void supprimerUtilisateur(User user) {
        try {
            System.out.println(user.getId());

            userService.supprimerUser(user);


            tableView.getItems().remove(user);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    private void banneruser(User user) {
        try {


            userService.bannneruser(user);


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void unbanneruser(User user) {
        try {

            userService.unbannneruser(user);


        } catch (SQLException e) {
            e.printStackTrace();

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


    public void pageupdate(ActionEvent event) throws IOException {
        // Charger la nouvelle scène
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inscription.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);

        // Obtenir la fenêtre actuelle (celle qui déclenche l'événement)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fermer la fenêtre actuelle
        currentStage.close();

        // Ouvrir la nouvelle fenêtre
        Stage stage = new Stage();
        stage.setTitle("Inscription Page");
        stage.setScene(scene);
        stage.show();
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
        String nomInput = nom.getText();
        int compteur =0;

                if( nomInput==null || nomInput.equals("")  ||nomInput.length()<3 ) {
                    showError("nomInvalide",errornom);
                    System.out.println("nom invalide");
compteur++;
                }
                else{
                    errornom.setOpacity(0);
                }
        String prenomInput = prenom.getText();
                if( prenomInput==null || prenomInput.equals("")  ||prenomInput.length()<3 ) {
                    showError("PrenomInvalide",prenomError);
                    System.out.println("Prenom invalide");
compteur ++;
                }
                else{
                    prenomError.setOpacity(0);
                }



        String emailInput = email.getText();
                User user12 = userService.recherparemail(emailInput);



                if (emailInput.isEmpty() || !emailInput.matches("^[\\w.-]+@(gmail\\.com|esprit\\.tn)$") || user12 != null) {
                    showError("Email invalide",emailError);
                    System.out.println("Email valide");
                    compteur++;
                } else {
                    System.out.println("Email invalide");
                    emailError.setOpacity(0);
                }

        String phoneInput1 = numTelephone.getText();

        if (!phoneInput1.matches("[0-9]{8}")) {
            showError("Numéro invalide",numTelephoneError);
compteur ++;
            System.out.println("numTelephone invalide");
        }
        else{
            numTelephoneError.setOpacity(0);
        }
        //lena probléme


        String passwordInput = password.getText();
        if( passwordInput==null || passwordInput.equals("")  ||passwordInput.length()<3 ) {
            compteur ++;
            showError("Password Invalide",passwordError);
            System.out.println("Password invalide");

        }
        else{
            passwordError.setOpacity(0);
        }
        String hashedPassword = BCrypt.hashpw(passwordInput, BCrypt.gensalt());

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



        String localisationInput = localisation.getText();
        if (localisationInput.length()<3 || localisationInput.equals("") ) {

            showError("Localisation invalide",localisationError);
            System.out.println("Localisation invalide");
            compteur ++;
        }
        else{
            localisationError.setOpacity(0);
            System.out.println("Localisation valide");
        }


        String[] roles = new String[]{"ROLE_USER"};
        RadioButton selectedRadioButton = (RadioButton) roleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedText = selectedRadioButton.getText();
            if( selectedText.equalsIgnoreCase("Admin") ){
                roles = new String[]{"ROLE_ADMIN"};
            }
            rolesError.setOpacity(0);
        } else {
            showError("selectionnez Roles",rolesError);
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





        if (compteur ==0) {
            int phoneInput = Integer.parseInt(numTelephone.getText());
            User user = new User(nomInput, prenomInput, emailInput, phoneInput, dateNaissanceInput, localisationInput, imagePathInput, hashedPassword, confirmPasswordInput, roles);
            try {

                userService.ajouterUser(user);


                List<User> users = userService.afficherdetailsuser();
                ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
                tableView.setItems(observableUsers);

                // Réinitialiser les champs après l'ajout
                nom.clear();
                prenom.clear();
                numTelephone.clear();
                email.clear();
                password.clear();
                confirmPassword.clear();
                dateNaissance.setValue(null);
                roleGroup.selectToggle(null);
                localisation.clear();
                imagePathLabel.setText("");

            } catch (SQLException e) {
                e.printStackTrace();
                showError("Une erreur est survenue lors du chargement de la fenêtre suivante.");
            }
        }
        else{
            return;
        }
    }


    @FXML
    private void search() throws SQLException {

        String nomsearch = searchField.getText().trim();
        if(nomsearch.length()!=0){
        List<User> list =    userService.recherparnom(nomsearch);
        ObservableList<User> observableList = FXCollections.observableArrayList(list);
        tableView.setItems(observableList);}
        else{
            List<User> list =  userService.afficherdetailsuser();
            ObservableList<User> observableList = FXCollections.observableArrayList(list);
            tableView.setItems(observableList);
        }
     System.out.println(nomsearch);
     System.out.println(userService.recherparnom(nomsearch).size());

    }

    private void showError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setOpacity(1); // Rendre le label visible
    }
    private void showError(String message,Label nomlabel) {
        nomlabel.setText(message);
        nomlabel.setOpacity(1);
    }
    public void affichertest() throws IOException {
       this.pageupdate();
    }


    public void logout() throws IOException {
        UserSessionManager.getInstance().logout();
        Stage currentStage = (Stage) imageView.getScene().getWindow();
        navbarController.changeScene("hello-view.fxml", currentStage);
    }
    public void pagedashboard() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("user.fxml", currentStage);
    }
    public void pageupdate() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("updateback.fxml", currentStage);
    }

    public void pageAssociation() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Benevolat/Association/affichageBack.fxml", currentStage);
    }
    public void pageReclamation() throws IOException {

        Stage currentStage = (Stage) circle1.getScene().getWindow();
        navbarController.changeScene("/Reclamation/reclamationBack.fxml", currentStage);
    }




}
