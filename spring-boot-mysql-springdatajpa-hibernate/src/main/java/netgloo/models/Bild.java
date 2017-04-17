package netgloo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
@Table(name = "bild")
public class Bild implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer privat;
    @Lob
    private byte[] datei;
    @Lob
    private byte[] thumbnail;
    private String erzeuger;
    @OneToOne
    private Preis preis;
    @OneToOne
    private Bildgruppe bildgruppe;


    public Bild() {
    }

    public Bild(Integer privat, byte[] datei, byte[] thumbnail, String erzeuger, Preis preis, Bildgruppe bildgruppe) {
        this.privat = privat;
        this.datei = datei;
        this.thumbnail = thumbnail;
        this.erzeuger = erzeuger;
        this.preis = preis;
        this.bildgruppe = bildgruppe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPrivat() {
        return privat;
    }

    public void setPrivat(Integer privat) {
        this.privat = privat;
    }

    public byte[] getDatei() {
        return datei;
    }

    public void setDatei(byte[] datei) {
        this.datei = datei;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getErzeuger() {
        return erzeuger;
    }

    public void setErzeuger(String erzeuger) {
        this.erzeuger = erzeuger;
    }

    public Preis getPreis() {
        return preis;
    }

    public void setPreis(Preis preis) {
        this.preis = preis;
    }

    public Bildgruppe getBildgruppe() {
        return bildgruppe;
    }

    public void setBildgruppe(Bildgruppe bildgruppe) {
        this.bildgruppe = bildgruppe;
    }
}
