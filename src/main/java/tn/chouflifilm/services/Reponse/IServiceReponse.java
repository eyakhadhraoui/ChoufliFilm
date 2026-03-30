package tn.chouflifilm.services.Reponse;

import tn.chouflifilm.entities.Reclamation;
import tn.chouflifilm.entities.Reponse;

import java.sql.SQLException;

public interface IServiceReponse<T> {
    public Reponse getReponse(T t) throws SQLException;
    public void  ajouterReponse(Reponse reponse) throws SQLException;
}
