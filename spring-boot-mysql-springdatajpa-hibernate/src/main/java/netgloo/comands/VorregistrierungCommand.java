package netgloo.comands;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by mazi on 19.07.17.
 */
public class VorregistrierungCommand {

    @NotEmpty()
    @Size(min = 6, max = 90,message = "Name muss zwischen 6 und 90 Zeichen lang sein")
    private String name;
    @NotEmpty(message = "Email darf nicht leer sein")
    @Size(min = 6, max = 50,message = "Email muss zwischen 6 und 50 Zeichen lang sein")
    private String email;
    @NotEmpty(message = "Telefonnummer darf nicht leer sein")
    @Size(min = 6, max = 50,message = "Telefonnummer muss zwischen 6 und 50 Zeichen lang sein")
    private String tel;
    @NotEmpty(message = "Strasse darf nicht leer sein")
    @Size(min = 6, max = 50,message = "Strasse muss zwischen 6 und 50 Zeichen lang sein")
    private String strasse;
    @NotEmpty(message = "Ort darf nicht leer sein")
    @Size(min = 4, max = 50,message = "Ort muss zwischen 4 und 50 Zeichen lang sein")
    private String ort;
    @NotEmpty(message = "Land darf nicht leer sein")
    @Size(min = 4, max = 50,message = "Land muss zwischen 4 und 50 Zeichen lang sein")
    private String land;
    @NotEmpty(message = "PLZ darf nicht leer sein")
    @Size(min = 4, max = 4,message = "PLZ muss 4 Zeichen lang sein")
    private String plz;
    @NotEmpty(message = "Koordinaten dürfen nicht leer sein")
    @Size(min = 10, max = 60,message = "Die Koordinaten müssen zwischen 10 und 60 Zeichen lang sein")
    @Pattern(regexp = ".*")
    private String koordinaten;


    public VorregistrierungCommand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
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

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getKoordinaten() {
        return koordinaten;
    }

    public void setKoordinaten(String koordinaten) {
        this.koordinaten = koordinaten;
    }
}
