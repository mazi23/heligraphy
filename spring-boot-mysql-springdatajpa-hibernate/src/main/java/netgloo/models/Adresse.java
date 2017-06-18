package netgloo.models;

import javax.persistence.*;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
@Table(name = "adresse")
public class Adresse {
    private long id;

    private String name;
    private String anschrift;
    private int plz;
    private String ort;
    private String land;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }



    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }



    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }



    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Adresse(long id) {
        this.id = id;
    }

    public Adresse(long id, String anschrift, int plz, String ort, String land) {
        this.id = id;
        this.anschrift = anschrift;
        this.plz = plz;
        this.ort = ort;
        this.land = land;
    }

    public Adresse() {
    }


}
