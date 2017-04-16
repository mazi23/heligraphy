package netgloo.models.DisplayObjects;

/**
 * Created by mazi on 21.01.17.
 */
public class BackendBild {

    int id;
    byte[] bild;
    int bildid;

    public int getBildid() {
        return bildid;
    }

    public void setBildid(int bildid) {
        this.bildid = bildid;
    }

    public BackendBild(int id, byte[] bild) {
        this.id = id;
        this.bild = bild;
    }

    public BackendBild() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getBild() {
        return bild;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }
}
