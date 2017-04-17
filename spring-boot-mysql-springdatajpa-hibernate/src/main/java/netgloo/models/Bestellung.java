package netgloo.models;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
@Table(name = "bestellung")
public class Bestellung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idBestellung;
    private Integer summe;
    private Integer mwst;
    private Timestamp datum;
    @OneToMany
    private Set<Bild> bilder = new HashSet<>();


    public Bestellung() {
    }

    public Bestellung(long idBestellung) {
        this.idBestellung = idBestellung;
    }

    public long getIdBestellung() {
        return idBestellung;
    }

    public void setIdBestellung(long idBestellung) {
        this.idBestellung = idBestellung;
    }

    public Integer getSumme() {
        return summe;
    }

    public void setSumme(Integer summe) {
        this.summe = summe;
    }

    public Integer getMwst() {
        return mwst;
    }

    public void setMwst(Integer mwst) {
        this.mwst = mwst;
    }



    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    public Bestellung(Integer summe, Integer mwst, Timestamp datum) {
        this.summe = summe;
        this.mwst = mwst;
        this.datum = datum;
    }
}
