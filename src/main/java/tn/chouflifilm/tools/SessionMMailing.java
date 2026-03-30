package tn.chouflifilm.tools;
import tn.chouflifilm.entities.User;
public class SessionMMailing {

    private static UserSessionManager instance;


    private User currentUser;


    public SessionMMailing() {}

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


