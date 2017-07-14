package netgloo.models.DisplayObjects;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by mazi on 17.04.17.
 */
public class BildDetailObject {

    int id;

    public Date aufnahmeDatum;
    public HashMap<String,String> metadata;

    public BildDetailObject(int id, Date aufnahmeDatum, HashMap<String, String> metadata) {
        this.id = id;

        this.aufnahmeDatum = aufnahmeDatum;
        this.metadata = metadata;
    }


    public BildDetailObject() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Date getAufnahmeDatum() {
        return aufnahmeDatum;
    }

    public void setAufnahmeDatum(Date aufnahmeDatum) {
        this.aufnahmeDatum = aufnahmeDatum;
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(HashMap<String, String> metadata) {
        this.metadata = metadata;
    }
}
