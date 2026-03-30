package tn.chouflifilm.entities;

import java.util.Objects;

public class ressource {
    private int id;
    private int association_id;
    private int user_id;
    private String besoin_specifique;
    private User user;
    private Association association;

public ressource() {}
    public ressource(int association_id, int user_id, String besoin_specifique) {
        this.association_id = association_id;
        this.user_id = user_id;
        this.besoin_specifique = besoin_specifique;
    }

    public ressource(int id,int association_id, int user_id, String besoin_specifique) {
    this.id = id;
        this.association_id = association_id;
        this.user_id = user_id;
        this.besoin_specifique = besoin_specifique;
    }
    @Override
    public String toString() {
        return "ressource{" +
                "id=" + id +
                ", association_id=" + association_id +
                ", user_id=" + user_id +
                ", besoin_specifique='" + besoin_specifique + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ressource ressource = (ressource) o;
        return id == ressource.id && association_id == ressource.association_id && user_id == ressource.user_id && Objects.equals(besoin_specifique, ressource.besoin_specifique);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, association_id, user_id, besoin_specifique);
    }

    public void setAssociation_id(int association_id) {
        this.association_id = association_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setBesoin_specifique(String besoin_specifique) {
        this.besoin_specifique = besoin_specifique;
    }

    public int getId() {
        return id;
    }

    public int getAssociation_id() {
        return association_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getBesoin_specifique() {
        return besoin_specifique;
    }
    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }
    public Association getAssociation() {return association;}
    public void setAssociation(Association association) {
    this.association = association;
    }
}
