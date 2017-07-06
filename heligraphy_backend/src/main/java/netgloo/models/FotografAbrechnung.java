package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;


/**
 * Created by mazi on 06.05.17.
 */
@Entity(name = "FotografAbrechnung")
public class FotografAbrechnung {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Fotograf fotograf;
    @OneToOne
    private Preis preis;
    private double summe;
    //true wenn bereits abgerechnet wurde
    private boolean abgerechnet;
    private Date gekauftwann;
    private Date abrechnungsDatum;


    public FotografAbrechnung() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Fotograf getFotograf() {
        return fotograf;
    }

    public void setFotograf(Fotograf fotograf) {
        this.fotograf = fotograf;
    }

    public Preis getPreis() {
        return preis;
    }

    public void setPreis(Preis preis) {
        this.preis = preis;
    }

    public double getSumme() {
        return summe;
    }

    public void setSumme(double summe) {
        this.summe = summe;
    }

    public boolean isAbgerechnet() {
        return abgerechnet;
    }

    public void setAbgerechnet(boolean abgerechnet) {
        this.abgerechnet = abgerechnet;
    }

    public Date getGekauftwann() {
        return gekauftwann;
    }

    public void setGekauftwann() {
        this.gekauftwann = new Date(System.currentTimeMillis());
    }

    public Date getAbrechnungsDatum() {
        return abrechnungsDatum;
    }

    public void setAbrechnungsDatum(Date abrechnungsDatum) {
        this.abrechnungsDatum = abrechnungsDatum;
    }
}
