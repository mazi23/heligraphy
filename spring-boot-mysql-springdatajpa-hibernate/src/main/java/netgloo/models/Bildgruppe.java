package netgloo.models;

import javax.persistence.*;
import java.util.Random;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
public class Bildgruppe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User user;
    private String uniqCode;
    @OneToOne
    private Adresse adresse;



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

    public String getUniqCode() {
        return uniqCode;
    }

    public void setUniqCode(String uniqCode) {
        this.uniqCode = uniqCode;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Bildgruppe(User user, String uniqCode, Adresse adresse) {
        this.user = user;
        this.uniqCode = uniqCode;
        this.adresse = adresse;
    }

    public Bildgruppe() {
        Random random = new Random(System.currentTimeMillis());
         int i = random.nextInt();
         uniqCode = Integer.toString(Math.abs(i));
    }

}
