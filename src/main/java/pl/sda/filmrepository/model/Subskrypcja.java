package pl.sda.filmrepository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Subskrypcja {
    @Id
    String mail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subskrypcja that = (Subskrypcja) o;
        return Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
