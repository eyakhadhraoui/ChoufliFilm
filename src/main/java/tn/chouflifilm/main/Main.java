package tn.chouflifilm.main;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.ressource;
import tn.chouflifilm.services.LanguageDetection;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.services.Reclamation.serviceReclamation;
import tn.chouflifilm.services.Ressources.ressourceService;
import tn.chouflifilm.services.userService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
ressource ressource = new ressource(5,2,3,"food");

ressourceService ressourceService = new ressourceService();
associationService associationService = new associationService();
userService userService = new userService();
Association association = new Association(2,"","mail@gmail.com","tunis",2222,"a.jpg","nous somme une nouvelle association");
serviceReclamation serviceReclamation = new serviceReclamation();
List<Reclamation> list =  serviceReclamation.getListReclamations();
System.out.println(list);

    }
}
