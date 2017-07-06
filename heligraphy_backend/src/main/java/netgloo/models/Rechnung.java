package netgloo.models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by mazi on 04.07.17.
 */
@Entity
public class Rechnung {

    @Id
    @GeneratedValue
    private long id;
    private Date datum;
    private Blob rechnung;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Blob getRechnung() {
        return rechnung;
    }

    public void setRechnung(Blob rechnung) {
        this.rechnung = rechnung;
    }

    public Rechnung() {
        datum = new Date();
    }
}
