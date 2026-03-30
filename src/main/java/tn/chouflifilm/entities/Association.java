package tn.chouflifilm.entities;

import java.util.Objects;

public class Association   {
    private int id;
    private String nom;
    private String mail_association;
    private String adresse;
    private int num_tel;
    private String image;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association that = (Association) o;
        return id == that.id && num_tel == that.num_tel && Objects.equals(nom, that.nom) && Objects.equals(mail_association, that.mail_association) && Objects.equals(adresse, that.adresse) && Objects.equals(image, that.image) && Objects.equals(description, that.description);
    }
public Association() {}
    public Association(String nom, String mail_association, String adresse, int num_tel, String image, String description) {
        this.nom = nom;
        this.mail_association = mail_association;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.image = image;
        this.description = description;
    }
    public Association(int id,String nom, String mail_association, String adresse, int num_tel, String image, String description) {
        this.id = id;
        this.nom = nom;
        this.mail_association = mail_association;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.image = image;
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, mail_association, adresse, num_tel, image, description);
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", mail_association='" + mail_association + '\'' +
                ", adresse='" + adresse + '\'' +
                ", num_tel=" + num_tel +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMail_association(String mail_association) {
        this.mail_association = mail_association;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getMail_association() {
        return mail_association;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
