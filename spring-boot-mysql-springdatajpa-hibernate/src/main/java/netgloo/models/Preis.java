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


    public Preis(int preis, int mwst) {
        this.preis = preis;
        this.mwst = mwst;
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
}
