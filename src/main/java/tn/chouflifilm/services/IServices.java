package tn.chouflifilm.services;

import tn.chouflifilm.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T> {
    public void ajouterUser(T t) throws SQLException;
    public void supprimerUser(T user) throws SQLException;
    public void supprimerparid(int id) throws SQLException;

    public void supprimerAll() throws SQLException;

    public void modifier(String nom,String prenom,String email,String localisation,int numtel,String image,int id) throws SQLException;
    public void ajouterverificationcode(String verifcode,String email) throws SQLException;
    public void modifierpassword(String password,String confirmpassword,String mail) throws SQLException;
    public User  afficher(T user) throws SQLException;
    public List<T> afficherAll() throws SQLException;
    public User Authentification(String prenom,String nom) throws SQLException;
public List<T> recherparnom(String nom) throws SQLException;
    public  User recherparid(int id) throws SQLException;
    public  User recherparverificationcode(String verifcode) throws SQLException;
    public  User recherparemail(String email) throws SQLException;
    public List<T> afficherdetailsuser() throws SQLException;
    public void unbannneruser(User user) throws SQLException;
    public void bannneruser(User user) throws SQLException;



}
