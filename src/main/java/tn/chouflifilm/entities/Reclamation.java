package tn.chouflifilm.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Reclamation {
    public int id;
    public int user_id;
    public String image;
    public String type;
    public String status;
    public String Description;
    public Date created_at;
    public String priority;
public List<Reponse> listReponses = new ArrayList<>();
public List<User>  listUsers = new ArrayList<>();
 public User user;
public Reponse reponse;

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public User getUser() {
        return user;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public void setListReponses(List<Reponse> listReponses) {
        this.listReponses = listReponses;
    }

    public List<Reponse> getListReponses() {
        return listReponses;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return Description;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getPriority() {
        return priority;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", Description='" + Description + '\'' +
                ", created_at=" + created_at +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id && user_id == that.user_id && Objects.equals(image, that.image) && Objects.equals(type, that.type) && Objects.equals(Description, that.Description) && Objects.equals(created_at, that.created_at) && Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, image, type, Description, created_at, priority);
    }

    public Reclamation(User user, String image, String type,String status ,String description, Date created_at, String priority) {
        this.user_id = user.getId();
        this.image = image;
        this.type = type;
        Description = description;
        this.status = status;
        this.created_at = created_at;
        this.priority = priority;
    }
    public Reclamation(int id,User user, String image, String type,String status ,String description, Date created_at, String priority) {
        this.id = id;
        this.user_id = user.getId();
        this.image = image;
        this.type = type;
        Description = description;
        this.status = status;
        this.created_at = created_at;
        this.priority = priority;
    }
    public Reclamation() {}
}
