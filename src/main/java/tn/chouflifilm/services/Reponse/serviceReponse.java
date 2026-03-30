package tn.chouflifilm.services.Reponse;

import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.Reponse;
import tn.chouflifilm.tools.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class serviceReponse implements IServiceReponse<Reclamation> {
    Connection cnx;
    public  serviceReponse() {
        cnx= MyDataBase.getDataBase().getConnection();
    }
    @Override
    public Reponse getReponse(Reclamation reclamation) throws SQLException {
String sql ="SELECT * FROM reponse where reclamation_id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, reclamation.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Reponse reponse = new Reponse();
            reponse.setReponse(rs.getString("reponse"));
            reponse.setReclamation(reclamation);
            return reponse;
        }
return null;
    }

    @Override
    public void ajouterReponse(Reponse reponse) throws SQLException {
     String sql= "Insert into reponse (reclamation_id, reponse)" + " values(?,?)";
     PreparedStatement ps = cnx.prepareStatement(sql);

     ps.setInt(1, reponse.getReclamation_id());
        ps.setString(2, reponse.getReponse());
     ps.executeUpdate();

    }


}
