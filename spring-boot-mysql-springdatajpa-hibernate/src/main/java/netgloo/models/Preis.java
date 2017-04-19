package netgloo.models;

import javax.persistence.*;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
public class Preis {
    @Id
    @GeneratedValue
    private long id;
    private int preis;
    private int mwst;
    private String preisPlan;



    public Preis(int preis, int mwst, String preisPlan) {
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

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int getMwst() {
        return mwst;
    }

    public void setMwst(int mwst) {
        this.mwst = mwst;
    }

    public String getPreisPlan() {
        return preisPlan;
    }

    public void setPreisPlan(String preisPlan) {
        this.preisPlan = preisPlan;
    }
}
