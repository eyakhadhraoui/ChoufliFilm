package tn.chouflifilm.services.Reclamation;

import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IReclamationService {
    public List<Reclamation> getListReclamations() throws SQLException;
    public List<Reclamation> getListReclamations(User user) throws SQLException;
    public void ajouterReclamation (Reclamation reclamation) throws SQLException;
    public  void supprimerReclamation (int id) throws SQLException;
    public void modifierReclamation(String description,String image,String type,int id) throws SQLException;
    public void repondreReclamation(String status,int id) throws SQLException;
}
