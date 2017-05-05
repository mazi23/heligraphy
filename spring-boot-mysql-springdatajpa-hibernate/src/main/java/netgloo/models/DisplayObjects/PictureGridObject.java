package netgloo.models.DisplayObjects;

/**
 * Created by mazi on 05.05.17.
 */
public class PictureGridObject {

    Long bildid;
    byte[] thubnail;


    public PictureGridObject() {
    }

    public PictureGridObject(Long bildid, byte[] thubnail) {
        this.bildid = bildid;
        this.thubnail = thubnail;
    }

    public Long getBildid() {
        return bildid;
    }

    public void setBildid(Long bildid) {
        this.bildid = bildid;
    }

    public byte[] getThubnail() {
        return thubnail;
    }

    public void setThubnail(byte[] thubnail) {
        this.thubnail = thubnail;
    }
}
