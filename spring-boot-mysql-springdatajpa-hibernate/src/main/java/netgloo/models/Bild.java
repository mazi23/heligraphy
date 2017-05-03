package netgloo.models;

import javax.persistence.*;
import java.io.Serializable;

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
    @OneToOne
    private Fotograf fotograf;

    @OneToOne
    private Bildgruppe bildgruppe;


    public Bild() {
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

    public Fotograf getFotograf() {
        return fotograf;
    }

    public void setFotograf(Fotograf fotograf) {
        this.fotograf = fotograf;
    }

    public Bildgruppe getBildgruppe() {
        return bildgruppe;
    }

    public void setBildgruppe(Bildgruppe bildgruppe) {
        this.bildgruppe = bildgruppe;
    }
}
