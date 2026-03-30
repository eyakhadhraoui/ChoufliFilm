package tn.chouflifilm.entities;

import java.util.Objects;

public class Reponse {
    private int id;
    private int reclamation_id;
    private String reponse;
public Reclamation reclamation;
public Reponse() {}

    public Reponse(String reponse, int reclamation_id) {
        this.reponse = reponse;
        this.reclamation_id = reclamation_id;
    }

    public int getReclamation_id() {
        return reclamation_id;
    }

    public String getReponse() {
        return reponse;
    }

    public int getId() {
        return id;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reclamation_id=" + reclamation_id +
                ", reponse='" + reponse + '\'' +
                ", reclamation=" + reclamation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse1 = (Reponse) o;
        return id == reponse1.id && reclamation_id == reponse1.reclamation_id && Objects.equals(reponse, reponse1.reponse) && Objects.equals(reclamation, reponse1.reclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reclamation_id, reponse, reclamation);
    }
}
