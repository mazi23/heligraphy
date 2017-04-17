package netgloo.models.DisplayObjects;

import com.drew.metadata.StringValue;

import java.util.Date;

/**
 * Created by mazi on 17.04.17.
 */
public class BildDetailObject {

    int id;
    byte[] Bild;
    Date aufnahmeDatum;
    String fullImageSize;
    String fotograf;
    String preis;
    String height;
    String width;

    public BildDetailObject(byte[] bild, Date aufnahmeDatum, String fullImageSize, String fotograf, String preis, String height, String width) {
        Bild = bild;
        this.aufnahmeDatum = aufnahmeDatum;
        this.fullImageSize = fullImageSize;
        this.fotograf = fotograf;
        this.preis = preis;
        this.height = height;
        this.width = width;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BildDetailObject() {
    }

    public byte[] getBild() {
        return Bild;
    }

    public void setBild(byte[] bild) {
        Bild = bild;
    }

    public Date getAufnahmeDatum() {
        return aufnahmeDatum;
    }

    public void setAufnahmeDatum(Date aufnahmeDatum) {
        this.aufnahmeDatum = aufnahmeDatum;
    }

    public String getFullImageSize() {
        return fullImageSize;
    }

    public void setFullImageSize(StringValue fullImageSize) {
        this.fullImageSize = fullImageSize.toString();
    }

    public String getFotograf() {
        return fotograf;
    }

    public void setFotograf(String fotograf) {
        this.fotograf = fotograf;
    }

    public String getPreis() {
        return preis;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(StringValue height) {
        this.height = height.toString();
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(StringValue width) {
        this.width = width.toString();
    }
}
