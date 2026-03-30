package tn.chouflifilm.entities;
import java.util.Arrays;
import java.util.Date;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String image;
    private String[] roles;
    private int num_telephone;
    private Date DateNaissance;
    private String localisation;
    private int banned;
    private int deleted;
    private  String verification_code;
    private String confirmpassword;
    private String google_id;
    private int banned_at;
    private int nblogin;
    private  String verification_twilio;

    public String[] getRoles() {
        return roles;
    }

    public void setimage(String image) {
        this.image = image;
    }

            public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getimage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String[] role) {
        this.roles = role;
    }

    public void setNum_telephone(int num_telephone) {
        this.num_telephone = num_telephone;
    }

    public void setDateNaissance(Date dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public void setGoogle_id(String  google_id) {
        this.google_id = google_id;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public void setBanned_at(int banned_at) {
        this.banned_at = banned_at;
    }

    public void setNblogin(int nblogin) {
        this.nblogin = nblogin;
    }

    public void setVerification_twilio(String verification_twilio) {
        this.verification_twilio = verification_twilio;
    }



    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String[] getRole() {
        return roles;
    }

    public int getNum_telephone() {
        return num_telephone;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateNaissance() {
        return DateNaissance;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public int getDeleted() {
        return deleted;
    }

    public int getBanned() {
        return banned;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public int getBanned_at() {
        return banned_at;
    }

    public int getNblogin() {
        return nblogin;
    }

    public String getVerification_twilio() {
        return verification_twilio;
    }



    public User( String nom, String prenom,String email,int num_telephone,Date dateNaissance,String localisation,String imag,String Password,String confirmpassword,String[] role) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_telephone = num_telephone;
        this.DateNaissance = dateNaissance;
        this.localisation = localisation;
        this.password = Password;
        this.confirmpassword = confirmpassword;
        this.banned = 0;
        this.deleted = 0;
this.google_id="";
        this.nblogin = 0;
        this.image=imag;
        roles = role;

    }



    public User( String nom, String prenom,String email,int num_telephone,Date dateNaissance,String localisation,String imag,String Password,String confirmpassword) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_telephone = num_telephone;
        this.DateNaissance = dateNaissance;
        this.localisation = localisation;
        this.password = Password;
        this.confirmpassword = confirmpassword;
        this.banned = 0;
        this.deleted = 0;
        this.google_id="";
        this.nblogin = 0;
        this.image=imag;
        roles = new String[]{"ROLE_USER"};

    }







    public User( String nom, String prenom,String email,int num_telephone,Date dateNaissance,String localisation,String imag,String Password,String confirmpassword,String google_id) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_telephone = num_telephone;
        this.DateNaissance = dateNaissance;
        this.localisation = localisation;
        this.password = Password;
        this.confirmpassword = confirmpassword;
        this.banned = 0;
        this.deleted = 0;
this.google_id = google_id;
        this.nblogin = 0;
        this.image=imag;
        roles = new String[]{"ROLE_USER"};

    }
    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + Arrays.toString(roles) +
                ", num_telephone=" + num_telephone +
                ", DateNaissance=" + DateNaissance +
                ", localisation='" + localisation + '\'' +
                ", banned=" + banned +
                ", deleted=" + deleted +
                ", verification_code='" + verification_code + '\'' +
                ", confirmpassword='" + confirmpassword + '\'' +
                ", google_id='" + google_id + '\'' +
                ", banned_at=" + banned_at +
                ", nblogin=" + nblogin +
                ", verification_twilio='" + verification_twilio + '\'' +
                "image " + image + '\'' +

                '}';
    }
}
