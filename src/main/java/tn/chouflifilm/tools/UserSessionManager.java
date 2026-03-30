package tn.chouflifilm.tools;

import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.User;

public class UserSessionManager {
    // Instance unique de la classe (Singleton)
    private static UserSessionManager instance;
private Association association;
private Reclamation reclamation;
    private User currentUser;

    public Association getAssociation() {
        return association;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public UserSessionManager() {}

    // Méthode pour obtenir l'instance unique
    public static UserSessionManager getInstance() {
        if (instance == null) {
            instance = new UserSessionManager();
        }
        return instance;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }


    public User getCurrentUser() {
        return this.currentUser;
    }


    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    // Méthode pour déconnecter l'utilisateur
    public void logout() {
        this.currentUser = null;
    }
}