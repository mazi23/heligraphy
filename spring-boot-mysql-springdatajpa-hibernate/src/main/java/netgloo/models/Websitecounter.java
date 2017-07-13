package netgloo.models;

import javax.persistence.*;

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
}
