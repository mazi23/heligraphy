package netgloo.models.DisplayObjects;

/**
 * Created by mazi on 29.04.17.
 */
public class OverviewPrice {

    private double zwischenSumme;
    private double versandkosten;
    private double gesamtkosten;

    public OverviewPrice(double zwischenSumme, double versandkosten, double gesamtkosten) {
        this.zwischenSumme = zwischenSumme;
        this.versandkosten = versandkosten;
        this.gesamtkosten = gesamtkosten;
    }

    public OverviewPrice() {
    }

    public double getZwischenSumme() {
        return zwischenSumme;
    }

    public void setZwischenSumme(double zwischenSumme) {
        this.zwischenSumme = zwischenSumme;
    }

    public double getVersandkosten() {
        return versandkosten;
    }

    public void setVersandkosten(double versandkosten) {
        this.versandkosten = versandkosten;
    }

    public double getGesamtkosten() {
        return gesamtkosten;
    }

    public void setGesamtkosten(double gesamtkosten) {
        this.gesamtkosten = gesamtkosten;
    }
}
