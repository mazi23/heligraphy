package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
public class Preis {
    @Id
    @GeneratedValue
    private long id;
    private double preis;
    private int mwst;
    private PreisPlan preisPlan;


    public Preis(double preis, int mwst, PreisPlan preisPlan) {
        this.preis = preis;
        this.mwst = mwst;
        this.preisPlan = preisPlan;
    }

    public Preis() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getMwst() {
        return mwst;
    }

    public void setMwst(int mwst) {
        this.mwst = mwst;
    }

    public PreisPlan getPreisPlan() {
        return preisPlan;
    }

    public void setPreisPlan(PreisPlan preisPlan) {
        this.preisPlan = preisPlan;
    }
}
