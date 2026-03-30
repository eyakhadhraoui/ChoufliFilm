package tn.chouflifilm.services.Ressources;

import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.ressource;

import java.sql.SQLException;
import java.util.List;

public interface IServiceressource<T> {
    public void ajouterRessource(T objet) throws SQLException;
    public void supprimerRessource(T objet) throws SQLException;
    public void rechercherRessource(T objet);
    public List<T> getListRessource() throws SQLException;
    public List <T> getRessourceParAssociation(Association association) throws SQLException;



}
