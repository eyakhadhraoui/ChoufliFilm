package tn.chouflifilm.services.Association;
import tn.chouflifilm.entities.Association;

import java.sql.SQLException;
import java.util.List;

public interface IServicesAssociation<T>  {
    public void ajouterAssociation(Association association) throws SQLException;
    public void supprimerAssociation(int associationId) throws SQLException;
    public Association rechercherAssociation(int id) throws SQLException;
    public List<Association> afficherAssociations() throws SQLException ;
    public List<Association> search(String input) throws SQLException;
    public void modifierAssociation(String nom,String mail,int num,String pathImage,String description,int id) throws SQLException;
}
