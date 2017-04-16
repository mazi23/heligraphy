package netgloo.models.DisplayObjects;

/**
 * Created by mazi on 30.01.17.
 */
public class FrontendBild {

    int id;
    byte[] bild;
    int bildid;
    int price;


    public int getBildid() {
        return bildid;
    }

    public void setBildid(int bildid) {
        this.bildid = bildid;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
