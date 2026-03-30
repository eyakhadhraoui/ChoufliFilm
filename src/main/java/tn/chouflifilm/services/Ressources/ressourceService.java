package tn.chouflifilm.services.Ressources;
import tn.chouflifilm.entities.Association;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.entities.ressource;
import tn.chouflifilm.services.Association.associationService;
import tn.chouflifilm.tools.MyDataBase;
import tn.chouflifilm.services.userService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ressourceService implements IServiceressource<ressource>{


    Connection cnx;

    public ressourceService() {
        cnx= MyDataBase.getDataBase().getConnection();
    }

    @Override
    public void ajouterRessource(ressource objet)throws SQLException {
        String sql = "INSERT INTO ressource (association_id, user_id,besoin_specifique)" +
                "VALUES (?, ?, ?)";
  userService us = new userService();
  if(us.recherparid(objet.getUser_id()) == null){
      System.out.println("Utilisateur n'existe pas");
      return;
  }
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, objet.getAssociation_id());
        ps.setInt(2,objet.getUser_id());
        ps.setString(3, objet.getBesoin_specifique());
        ps.executeUpdate();
        System.out.println("Ressource Ajouté");
    }
    @Override
    public void supprimerRessource(ressource objet) throws SQLException {
String sql = "DELETE FROM ressource WHERE id = ?";
PreparedStatement ps = cnx.prepareStatement(sql);
ps.setInt(1, objet.getId());
ps.executeUpdate();

    }

    @Override
    public void rechercherRessource(ressource objet) {

    }

    @Override
    public List<ressource> getListRessource() throws SQLException {
     String sql = "SELECT * FROM ressource";
     Statement st = cnx.createStatement();
     ResultSet rs = st.executeQuery(sql);
     List<ressource> list = new ArrayList<ressource>();
     while (rs.next()) {
         int id = rs.getInt("id");
         int association_id = rs.getInt("association_id");
         int user_id = rs.getInt("user_id");
         String besoin_specifique = rs.getString("besoin_specifique");
         ressource ressource = new ressource(id, association_id, user_id, besoin_specifique);
         list.add(ressource);
         System.out.println(ressource);




     }
     return list;
    }

    @Override
    public List<ressource> getRessourceParAssociation(Association association) throws SQLException {
        String sql="Select * from ressource where association_id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, association.getId());
        ResultSet rs = ps.executeQuery();
        List<ressource> list = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("id");
            int association_id = rs.getInt("association_id");
            int user_id = rs.getInt("user_id");
            String besoin_specifique = rs.getString("besoin_specifique");
            ressource r = new ressource(id, association_id, user_id, besoin_specifique);
            list.add(r);
            System.out.println(r);
            userService us = new userService();
            User  user = us.recherparid(r.getUser_id());
            r.setUser(user);
            associationService asssocitionserv = new associationService();
            Association association1=  asssocitionserv.rechercherAssociation(r.getAssociation_id());
            r.setAssociation(association);

        }
        return list;

    }





}
