package tn.chouflifilm.services.Reclamation;

import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.Reponse;
import tn.chouflifilm.entities.User;
import tn.chouflifilm.services.Reponse.serviceReponse;
import tn.chouflifilm.services.userService;
import tn.chouflifilm.tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class serviceReclamation implements IReclamationService{
Connection cnx;
    public serviceReclamation() {
        cnx= MyDataBase.getDataBase().getConnection();
    }

serviceReponse  serviceReponse = new serviceReponse();


    @Override
    public List<Reclamation> getListReclamations() throws SQLException {
     String sql = "Select * from Reclamation";
     Statement stmt = cnx.createStatement();
     ResultSet rs = stmt.executeQuery(sql);
        List<Reclamation> listReclamation = new ArrayList<>();
     while (rs.next()){
         int id = rs.getInt("id");
         int user_id = rs.getInt("user_id");
         String image = rs.getString("image");
         String status = rs.getString("status");
         String type = rs.getString("type");
         String description = rs.getString("description");
         Date created_at = rs.getDate("created_at");
         String priority = rs.getString("priority");
         userService userService = new userService();
         User user =    userService.recherparid(user_id);
         Reclamation reclamation = new Reclamation(id,user, image,type ,status, description, created_at, priority);
         Reponse reponse = serviceReponse.getReponse(reclamation);
         reclamation.setReponse(reponse);
         reclamation.setUser(user);
         listReclamation.add(reclamation);
     }
return listReclamation;

    }

    @Override
    public List<Reclamation> getListReclamations(User user1) throws SQLException {
        String sql = "SELECT * FROM Reclamation WHERE user_id = ?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setInt(1, user1.getId()); // ajout du paramètre

        ResultSet rs = stmt.executeQuery();
        List<Reclamation> listReclamation = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            String image = rs.getString("image");
            String status = rs.getString("status");
            String type = rs.getString("type");
            String description = rs.getString("description");
            Date created_at = rs.getDate("created_at");
            String priority = rs.getString("priority");

            userService userService = new userService();
            User user = userService.recherparid(user_id);

            Reclamation reclamation = new Reclamation(id, user, image, type, status, description, created_at, priority);
            Reponse reponse = serviceReponse.getReponse(reclamation);
            reclamation.setReponse(reponse);
            reclamation.setUser(user);

            listReclamation.add(reclamation);
        }

        return listReclamation;
    }



    @Override
    public void ajouterReclamation(Reclamation reclamation) throws SQLException {
        String sql = "INSERT INTO reclamation ( user_id, image, type, status, description, created_at, priority) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setInt(1, reclamation.getUser_id());
        stmt.setString(2, reclamation.getImage());
        stmt.setString(3, reclamation.getType());
        stmt.setString(4, reclamation.getStatus());
        stmt.setString(5, reclamation.getDescription());
        stmt.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        stmt.setString(7,"medium");
        stmt.executeUpdate();
        System.out.println("Reclamation Ajouté avec succés");


    }

    @Override
    public void supprimerReclamation(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement psReponse = null;
        PreparedStatement psReclamation = null;

        try {
            connection = cnx;
            connection.setAutoCommit(false);  // Début de la transaction

            // 1. Supprimer d'abord la réponse associée (relation one-to-one)
            String sqlReponse = "DELETE FROM reponse WHERE reclamation_id = ?";
            psReponse = connection.prepareStatement(sqlReponse);
            psReponse.setInt(1, id);
            psReponse.executeUpdate();

            // 2. Ensuite supprimer la réclamation
            String sqlReclamation = "DELETE FROM reclamation WHERE id = ?";
            psReclamation = connection.prepareStatement(sqlReclamation);
            psReclamation.setInt(1, id);
            int rowsAffected = psReclamation.executeUpdate();

            connection.commit();  // Valider la transaction

            if (rowsAffected > 0) {
                System.out.println("Réclamation et sa réponse supprimées avec succès");
            } else {
                System.out.println("Aucune réclamation trouvée avec l'ID: " + id);
            }
        } catch (SQLException e) {
            // En cas d'erreur, annuler la transaction
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Erreur lors du rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            throw e;
        } finally {
            // Rétablir l'autocommit et fermer les ressources
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException autoCommitEx) {
                    System.err.println("Erreur lors du rétablissement de l'autocommit: " + autoCommitEx.getMessage());
                }
            }
            if (psReponse != null) psReponse.close();
            if (psReclamation != null) psReclamation.close();
        }
    }

    @Override
    public void modifierReclamation(String description, String image, String type, int id) throws SQLException {
        String sql = "Update reclamation Set image=?,type=?,description=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1,image);
        ps.setString(2,type);
        ps.setString(3,description);
        ps.setInt(4,id);
        ps.executeUpdate();
        System.out.println("Réclamation modifié avec succés");
    }



    @Override
    public void repondreReclamation(String status, int id) throws SQLException {
        String sql = "Update reclamation Set status =? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1,status);
        ps.setInt(2,id);
        ps.executeUpdate();
        System.out.println("Status réglé");
    }
}
