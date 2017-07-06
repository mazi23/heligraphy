package netgloo.models.reportObjects;


import java.util.Date;

/**
 * Created by mazi on 07.05.17.
 */
public class Abrechnung {


    private double preis;
    private double anteil;
    private Date kaufdatum;


    public Abrechnung() {
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getAnteil() {
        return anteil;
    }

    public void setAnteil(double anteil) {
        this.anteil = anteil;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }
}
