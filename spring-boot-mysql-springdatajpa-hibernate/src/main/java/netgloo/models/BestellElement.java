package netgloo.models;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mazi on 30.04.17.
 */
@Entity
@Table(name = "BestellElement")
public class BestellElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int stueck;
    private String bezeichnung;
    private double preis;
    private int bildID;

    public BestellElement(int stueck, String bezeichnung, double preis, int bildID) {
        this.stueck = stueck;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.bildID = bildID;
    }

    public BestellElement() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStueck() {
        return stueck;
    }

    public void setStueck(int stueck) {
        this.stueck = stueck;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getBildID() {
        return bildID;
    }

    public void setBildID(int bildID) {
        this.bildID = bildID;
    }
}
