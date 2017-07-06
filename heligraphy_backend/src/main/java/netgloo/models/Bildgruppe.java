package netgloo.models;

import javax.persistence.*;
import java.util.Date;
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

    private boolean verkauft;

    private boolean gedruckt;

    private Date gedruckwann;

    public boolean isVerkauft() {
        return verkauft;
    }

    public void setVerkauft(boolean verkauft) {
        this.verkauft = verkauft;
    }

    public boolean isGedruckt() {
        return gedruckt;
    }

    public void setGedruckt(boolean gedruckt) {
        this.gedruckt = gedruckt;
    }

    public Date getGedruckwann() {
        return gedruckwann;
    }

    public void setGedruckwann(Date gedruckwann) {
        this.gedruckwann = gedruckwann;
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
