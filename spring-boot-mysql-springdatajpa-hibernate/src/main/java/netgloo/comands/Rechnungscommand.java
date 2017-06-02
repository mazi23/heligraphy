package netgloo.comands;

import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mazi on 31.05.17.
 */
public class Rechnungscommand {

    private AdressenCommand versandadresse;
    private AdressenCommand rechnungsadresse;
    private List<ShoppingCartItem> positionen;

    private String vaName;
    private String reName;

    private double summe;
    private double mwstSumme;
    private double nettoSumme;
    private double versandkosten;

    private Date datum;
    private int reNummer;

    public Rechnungscommand() {
        positionen = new LinkedList<>();
        for (int i = 0;i<6;i++){
            ShoppingCartItem s = new ShoppingCartItem();
            //s.setId(i);
            positionen.add(s);
        }
    }

    public String getVaName() {
        return vaName;
    }

    public void setVaName(String vaName) {
        this.vaName = vaName;
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public AdressenCommand getVersandadresse() {
        return versandadresse;
    }

    public void setVersandadresse(AdressenCommand versandadresse) {
        this.versandadresse = versandadresse;
    }

    public AdressenCommand getRechnungsadresse() {
        return rechnungsadresse;
    }

    public void setRechnungsadresse(AdressenCommand rechnungsadresse) {
        this.rechnungsadresse = rechnungsadresse;
    }

    public List<ShoppingCartItem> getPositionen() {
        return positionen;
    }

    public void setPositionen(List<ShoppingCartItem> positionen) {
        this.positionen = positionen;
    }

    public double getSumme() {
        return summe;
    }

    public void setSumme(double summe) {
        this.summe = summe;
    }

    public double getMwstSumme() {
        return mwstSumme;
    }

    public void setMwstSumme(double mwstSumme) {
        this.mwstSumme = mwstSumme;
    }

    public double getNettoSumme() {
        return nettoSumme;
    }

    public void setNettoSumme(double nettoSumme) {
        this.nettoSumme = nettoSumme;
    }

    public double getVersandkosten() {
        return versandkosten;
    }

    public void setVersandkosten(double versandkosten) {
        this.versandkosten = versandkosten;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getReNummer() {
        return reNummer;
    }

    public void setReNummer(int reNummer) {
        this.reNummer = reNummer;
    }
}
