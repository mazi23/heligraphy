package netgloo.models.DisplayObjects;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by mazi on 21.01.17.
 */
public class UploaderObject {


    String adresse,plz,ort,land;
    List<MultipartFile> bilder;
    Integer preis;
    String fotograf;


    public String getFotograf() {
        return fotograf;
    }

    public void setFotograf(String fotograf) {
        this.fotograf = fotograf;
    }

    public UploaderObject() {
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public List<MultipartFile> getBilder() {
        return bilder;
    }

    public void setBilder(List<MultipartFile> bilder) {
        this.bilder = bilder;
    }

    public UploaderObject(String adresse, String plz, String ort, String land, List<MultipartFile> bilder) {

        this.adresse = adresse;
        this.plz = plz;
        this.ort = ort;
        this.land = land;
        this.bilder = bilder;
    }

    public UploaderObject(String adresse, String plz, String ort, String land, List<MultipartFile> bilder, Integer preis, String fotograf) {
        this.adresse = adresse;
        this.plz = plz;
        this.ort = ort;
        this.land = land;
        this.bilder = bilder;
        this.preis = preis;
        this.fotograf = fotograf;
    }

    public Integer getPreis() {
        return preis;
    }

    public void setPreis(Integer preis) {
        this.preis = preis;
    }
}
