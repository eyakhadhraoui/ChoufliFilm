package tn.chouflifilm.services;
import com.google.gson.Gson;
import tn.chouflifilm.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import tn.chouflifilm.tools.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;
public class userService implements IServices<User>{

    Connection cnx;

    public userService() {
        cnx=MyDataBase.getDataBase().getConnection();
    }

    @Override
    public void ajouterUser(User user) throws SQLException {
        String sql = "INSERT INTO user (nom, prenom, email, num_telephone, date_naissance, localisation, image, roles, password, banned, banned_until, deleted, verification_code, confirm_password, google_id, banned_at, nblogin, verification_twilio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";



        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1, user.getNom());
        ps.setString(2, user.getPrenom());
        ps.setString(3, user.getEmail());
        ps.setInt(4, user.getNum_telephone());
        ps.setDate(5, new java.sql.Date(user.getDateNaissance().getTime()));
        ps.setString(6, user.getLocalisation());
        ps.setString(7, user.getimage());
        Gson gson = new Gson();
        String rolesJson = gson.toJson(user.getRoles()); // Convertit le tableau en JSON
        ps.setString(8, rolesJson);
        ps.setString(9, user.getPassword());
        ps.setInt(10, 0); // banned
        ps.setInt(11, 0);
        ps.setInt(12, 0); // deleted
        ps.setString(13, user.getVerification_code());
        ps.setString(14, user.getConfirmpassword());
        ps.setString(15, user.getGoogle_id()); // googleid
        ps.setInt(16, 0); // bannedat
        ps.setInt(17, 0); // nblogin
        ps.setString(18, user.getVerification_twilio());


        ps.executeUpdate();
        System.out.println("User Ajouté");
    }


    @Override
    public void supprimerUser(User user) throws SQLException {
        String sql = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Utilisateur supprimé avec cascade");
            } else {
                System.out.println("Aucun utilisateur trouvé");
            }
        }
    }


    @Override
    public void supprimerparid(int  id) throws SQLException {
        String sql = "delete from user where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
        System.out.println("Utilisateur Supprimé");
    }

    @Override
    public void supprimerAll() throws SQLException {
     String sql = "delete from user1";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.executeUpdate();
        System.out.println("Tous les utilisateurs sont supprimés ");
    }

    @Override
    public void modifier(String nom,String prenom,String email,String localisation,int numtel,String image,int id) throws SQLException {
 String sql="update user  set nom=?, prenom=?,email=? , localisation=?, num_telephone=?,image=? where id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, email);
        ps.setString(4, localisation);
        ps.setInt(5, numtel);
        ps.setString(6,image);
        ps.setInt(7,id);
        ps.executeUpdate();
        System.out.println("Personne modifié avec succés ");

    }

   @Override
   public void ajouterverificationcode(String verifcode,String email) throws SQLException{
       String sql="update user  set verification_code=? where email=?";
       PreparedStatement ps = cnx.prepareStatement(sql);

       ps.setString(1, verifcode);
       ps.setString(2, email);

       ps.executeUpdate();
       System.out.println("code ajouté avec succées ");

   }



    @Override
    public void bannneruser(User user) throws SQLException{
        String sql = "UPDATE user SET banned = ? WHERE email = ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, 1);
            ps.setString(2, user.getEmail());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur avec ID " + user.getEmail()+ " banni avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec ID " + user.getEmail() + ".");
            }
        }
    }

    @Override
    public void unbannneruser(User user) throws SQLException{
        String sql="update user  set banned=? where email=?";
        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setInt(1,0);
        ps.setString(2, user.getEmail());

        ps.executeUpdate();
        System.out.println("code ajouté avec succées ");

    }


@Override
    public void modifierpassword(String password,String confirmpassword,String mail) throws SQLException{

    String sql="update user  set password=?,confirm_password=? where email=?";
    PreparedStatement ps = cnx.prepareStatement(sql);

    ps.setString(1, password);
    ps.setString(2, confirmpassword);
    ps.setString(3, mail);

    ps.executeUpdate();
    System.out.println("PASSWORD CHANGé avec succés ");

}





    @Override
    public User afficher(User user) throws SQLException {
  String sql = "select * from user1 where id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, user.getId());
        ps.executeQuery();
return null;
    }



    @Override
    public User Authentification(String email, String password) throws SQLException {
        String sql = "select * from user where email=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, email);


        ResultSet rs = ps.executeQuery();
        if (rs != null && rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setNum_telephone(rs.getInt("num_telephone"));
            user.setDateNaissance(rs.getDate("date_naissance"));
            user.setLocalisation(rs.getString("localisation"));
            user.setimage(rs.getString("image"));
            user.setPassword(rs.getString("password"));
            user.setBanned(rs.getInt("banned"));
            user.setDeleted(rs.getInt("deleted"));
            String rolesStr = rs.getString("roles");
            if (rolesStr != null && !rolesStr.isEmpty()) {
                String[] roles = rolesStr.split(",");  // Convertir la chaîne en tableau
                user.setRoles(roles);
            }
            boolean valid = BCrypt.checkpw(password, user.getPassword());
            if(valid == false){
               return null;
            }

            return user;
        }
        return null;
    }

    @Override
    public List<User> recherparnom(String nom) throws SQLException {
        String sql = "SELECT nom, prenom, email, num_telephone FROM user WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ? OR num_telephone LIKE ?";
        PreparedStatement ps1 = cnx.prepareStatement(sql);
        ps1.setString(1, "%" + nom + "%");  // Paramétrer le nom
        ps1.setString(2, "%" + nom + "%");
        ps1.setString(3, "%" + nom + "%");
        ps1.setString(4, "%" + nom + "%");


        ResultSet rs = ps1.executeQuery();  // Exécuter la requête avec ps1

        List<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setNum_telephone(rs.getInt("num_telephone")); // Récupérer le téléphone en tant que String
            users.add(user);
        }
        return users;
    }


    @Override
        public User recherparid(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id=?";
        User user = null;

        try (PreparedStatement ps1 = cnx.prepareStatement(sql)) {
            ps1.setInt(1, id);

            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setNum_telephone(rs.getInt("num_telephone"));
                    user.setDateNaissance(rs.getDate("date_naissance"));
                    user.setLocalisation(rs.getString("localisation"));
                    user.setimage(rs.getString("image"));
                    user.setPassword(rs.getString("password"));


                }
            }
        }
        return user;
    }


    @Override
    public User recherparemail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email=?";
        User user = null;

        try (PreparedStatement ps1 = cnx.prepareStatement(sql)) {
            ps1.setString(1, email);

            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setNum_telephone(rs.getInt("num_telephone"));
                    user.setDateNaissance(rs.getDate("date_naissance"));
                    user.setLocalisation(rs.getString("localisation"));
                    user.setimage(rs.getString("image"));
                    user.setPassword(rs.getString("password"));
                    user.setVerification_code(rs.getString("verification_code"));

                }
            }
        }
        return user;
    }


    @Override
    public User recherparverificationcode(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE verification_code=?";
        User user = null;

        try (PreparedStatement ps1 = cnx.prepareStatement(sql)) {
            ps1.setString(1, email);

            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setNum_telephone(rs.getInt("num_telephone"));
                    user.setDateNaissance(rs.getDate("date_naissance"));
                    user.setLocalisation(rs.getString("localisation"));
                    user.setimage(rs.getString("image"));
                    user.setPassword(rs.getString("password"));
                    user.setVerification_code(rs.getString("verification_code"));


                }
            }
        }
        return user;
    }








    @Override
    public List<User> afficherdetailsuser() throws SQLException {
        String sql = "SELECT nom, prenom, email, num_telephone ,banned ,image,roles FROM user";
        Statement ps = cnx.createStatement();
        ResultSet rs = ps.executeQuery(sql);
        List<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setNum_telephone(rs.getInt("num_telephone"));
user.setBanned(rs.getInt("banned"));
user.setimage(rs.getString("image"));
            String rolesStr = rs.getString("roles");
            if (rolesStr != null && !rolesStr.isEmpty()) {
                String[] roles = rolesStr.split(",");  // Convertir la chaîne en tableau
                user.setRoles(roles);
            }

            users.add(user);
        }
        return users;
    }




    @Override
    public List<User> afficherAll() throws SQLException {
        String sql = "select * from user1 ";
        Statement ps = cnx.createStatement();
       ResultSet rs = ps.executeQuery(sql);
    List<User> users = new ArrayList<User>();
    while (rs.next()) {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));

        users.add(user);
    }
return users;
    }

    /**
     * Récupère le nombre d'utilisateurs bannis groupés par mois
     * @return Une liste de Map contenant le mois et le nombre d'utilisateurs bannis
     * @throws SQLException si une erreur de base de données se produit
     */
    public List<Map<String, Object>> getBannedUsersByMonth() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT SUBSTRING(banned_at, 1, 3) AS month, COUNT(id) AS count " +
                "FROM user " +
                "WHERE banned = 1 AND banned_at IS NOT NULL " +
                "GROUP BY month " +
                "ORDER BY month ASC";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cnx.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                int count = rs.getInt("count");

                Map<String, Object> monthData = new HashMap<>();
                monthData.put("month", month);
                monthData.put("count", count);

                result.add(monthData);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return result;
    }

}
