package netgloo.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mazi on 13.07.17.
 */
@Entity
public class Websitecounter {

    @GeneratedValue
    @Id
    private long id;
    private String seite;
    private String ip;
    private Date datum;
    private String info;

    public Websitecounter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeite() {
        return seite;
    }

    public void setSeite(String seite) {
        this.seite = seite;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
