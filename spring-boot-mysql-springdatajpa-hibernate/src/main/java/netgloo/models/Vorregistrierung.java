package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by mazi on 19.07.17.
 */
@Entity
public class Vorregistrierung {

    @GeneratedValue
    @Id
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Adresse adresse;
    private String koordinaten;

    public Vorregistrierung() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getKoordinaten() {
        return koordinaten;
    }

    public void setKoordinaten(String koordinaten) {
        this.koordinaten = koordinaten;
    }
}
