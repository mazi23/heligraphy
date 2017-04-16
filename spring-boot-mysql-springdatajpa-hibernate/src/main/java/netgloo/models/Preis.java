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


}
