package netgloo.comands;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

/**
 * Created by mazi on 26.04.17.
 */
@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class AdressenCommand {

    @NotEmpty(message = "Name darf nicht leer sein")
    @Size(min = 4, max = 50,message = "Name muss zwischen 4 und 50 Zeichen lang sein")
    private String nameVA;


    @NotEmpty(message = "Email darf nicht leer sein.")
    @Size(min = 5, max = 50,message = "Email muss zwischen 5 und 50 Zeichen lang sein.")
    private String emailVA;

    private String telVA;
    @NotEmpty(message = "Strasse darf nicht leer sein")
    @Size(min = 3, max = 50, message = "Das Feld Straße muss mindestens 3 Zeichen lang sein.")
    private String strasseVA;
    @NotEmpty(message = "Das Feld Ort darf nicht leer sein")
    @Size(min = 3, max = 50, message = "Das Feld Ort muss mindestens 3 Zeichen lang sein.")
    private String ortVA;
    @NotEmpty(message = "Das Feld Land darf nicht leer sein")
    @Size(min = 3, max = 50, message = "Das Feld Land muss mindestens 3 Zeichen lang sein.")
    private String landVA;
    @NotEmpty(message = "Das feld PLZ darf nicht leer sein")
    @Size(min = 2, max = 5, message = "Das Feld PLZ muss 4 Zeichen lang sein.")
    private String plzVA;



    private String nameRA;


    private String emailRA;


    private String telRA;


    private String strasseRA;


    private String ortRA;


    private String landRA;


    private String plzRA;

    private String zahlungsart;


    public AdressenCommand(String nameVA, String emailVA, String telVA, String strasseVA, String ortVA, String landVA, String plzVA, String nameRA, String emailRA, String telRA, String strasseRA, String ortRA, String landRA, String plzRA, String zahlungsart) {
        this.nameVA = nameVA;
        this.emailVA = emailVA;
        this.telVA = telVA;
        this.strasseVA = strasseVA;
        this.ortVA = ortVA;
        this.landVA = landVA;
        this.plzVA = plzVA;
        this.nameRA = nameRA;
        this.emailRA = emailRA;
        this.telRA = telRA;
        this.strasseRA = strasseRA;
        this.ortRA = ortRA;
        this.landRA = landRA;
        this.plzRA = plzRA;
        this.zahlungsart = zahlungsart;
    }

    public AdressenCommand() {
    }

    public String getNameVA() {
        return nameVA;
    }

    public void setNameVA(String nameVA) {
        this.nameVA = nameVA;
    }

    public String getEmailVA() {
        return emailVA;
    }

    public void setEmailVA(String emailVA) {
        this.emailVA = emailVA;
    }

    public String getTelVA() {
        return telVA;
    }

    public void setTelVA(String telVA) {
        this.telVA = telVA;
    }

    public String getStrasseVA() {
        return strasseVA;
    }

    public void setStrasseVA(String strasseVA) {
        this.strasseVA = strasseVA;
    }

    public String getOrtVA() {
        return ortVA;
    }

    public void setOrtVA(String ortVA) {
        this.ortVA = ortVA;
    }

    public String getLandVA() {
        return landVA;
    }

    public void setLandVA(String landVA) {
        this.landVA = landVA;
    }

    public String getPlzVA() {
        return plzVA;
    }

    public void setPlzVA(String plzVA) {
        this.plzVA = plzVA;
    }

    public String getNameRA() {
        return nameRA;
    }

    public void setNameRA(String nameRA) {
        this.nameRA = nameRA;
    }

    public String getEmailRA() {
        return emailRA;
    }

    public void setEmailRA(String emailRA) {
        this.emailRA = emailRA;
    }

    public String getTelRA() {
        return telRA;
    }

    public void setTelRA(String telRA) {
        this.telRA = telRA;
    }

    public String getStrasseRA() {
        return strasseRA;
    }

    public void setStrasseRA(String strasseRA) {
        this.strasseRA = strasseRA;
    }

    public String getOrtRA() {
        return ortRA;
    }

    public void setOrtRA(String ortRA) {
        this.ortRA = ortRA;
    }

    public String getLandRA() {
        return landRA;
    }

    public void setLandRA(String landRA) {
        this.landRA = landRA;
    }

    public String getPlzRA() {
        return plzRA;
    }

    public void setPlzRA(String plzRA) {
        this.plzRA = plzRA;
    }

    public String getZahlungsart() {
        return zahlungsart;
    }

    public void setZahlungsart(String zahlungsart) {
        this.zahlungsart = zahlungsart;
    }
}
