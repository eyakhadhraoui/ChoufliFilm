package tn.chouflifilm.services.Association;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class associationService  implements IServicesAssociation<Association>{


    Connection cnx;

    public associationService() {
        cnx= MyDataBase.getDataBase().getConnection();
    }

    @Override
    public void ajouterAssociation(Association association) throws SQLException {
        String sql = "INSERT INTO association (nom, mail_association, adresse, num_tel, image, description)" +
                "VALUES (?, ?, ?, ?, ?, ?)";



        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1, association.getNom());
     ps.setString(2,association.getMail_association());
     ps.setString(3, association.getAdresse());
     ps.setInt(4, association.getNum_tel());
     ps.setString(5, association.getImage());
     ps.setString(6, association.getDescription());


        ps.executeUpdate();
        System.out.println("Association Ajouté");
    }


    @Override
    public void supprimerAssociation(int id) throws SQLException {
        String sql = "DELETE FROM association WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
System.out.println("Association Supprimer");
    }

    @Override
    public Association rechercherAssociation(int id) throws SQLException {
        String sql = "SELECT * FROM association WHERE id = ?";
        Association a = null;
PreparedStatement ps = cnx.prepareStatement(sql);
ps.setInt(1,id);
ResultSet rs = ps.executeQuery();
if(rs.next()){

    int id1 = rs.getInt("id");
    String nom = rs.getString("nom");
    String mail = rs.getString("mail_association");
    String adresse = rs.getString("adresse");
    int num_tel = rs.getInt("num_tel");
    String image = rs.getString("image");
    String description = rs.getString("description");
    Association association = new Association(id1,nom,mail,adresse,num_tel,image,description);
    System.out.println(association);
return association;
}
return null;
    }

    @Override
    public List<Association> afficherAssociations() throws SQLException  {
        String sql = "SELECT * FROM association";
       Statement ps = cnx.createStatement();
       ResultSet rs = ps.executeQuery(sql);
       List<Association> associations = new ArrayList<>();
       while(rs.next()) {
           int id = rs.getInt("id");
         String nom = rs.getString("nom");
         String mail = rs.getString("mail_association");
         String adresse = rs.getString("adresse");
         int num_tel = rs.getInt("num_tel");
         String image = rs.getString("image");
         String description = rs.getString("description");
         Association association = new Association(id,nom,mail,adresse,num_tel,image,description);
         associations.add(association);
         System.out.println(association);
       }
        return  associations;

    }

    @Override
    public List<Association> search(String input) throws SQLException {
        String sql = "SELECT nom, mail_association , adresse ,  num_tel ,description FROM association WHERE nom LIKE ? OR mail_association LIKE ? OR num_tel LIKE ? OR description LIKE ? OR adresse LIKE ?";
        PreparedStatement ps1 = cnx.prepareStatement(sql);
        ps1.setString(1, "%" + input + "%");  // Paramétrer le nom
        ps1.setString(2, "%" +input + "%");
        ps1.setString(3, "%" + input + "%");
        ps1.setString(4, "%" + input + "%");
        ps1.setString(5,  "%" + input + "%");


        ResultSet rs = ps1.executeQuery();  // Exécuter la requête avec ps1

        List<Association> users = new ArrayList<Association>();
        while (rs.next()) {
            Association user = new Association();
            user.setNom(rs.getString("nom"));
            user.setMail_association(rs.getString("mail_association"));
            user.setNum_tel(rs.getInt("num_tel"));
            user.setDescription(rs.getString("description"));
            user.setAdresse(rs.getString("adresse"));
            user.setImage(rs.getString("image"));

            users.add(user);
        }
        return users;
    }
    public void modifierAssociation(String nom,String mail,int num,String pathImage,String description,int id) throws SQLException{
        String sql =" Update association Set  nom =? ,mail_association =? ,num_tel =?, image=? ,description =? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1,nom);
        ps.setString(2,mail);
        ps.setInt(3,num);
        ps.setString(4,pathImage);
        ps.setString(5,description);
        ps.setInt(6,id);
        ps.executeUpdate();
        System.out.println("Association Modifier");
    }

}

