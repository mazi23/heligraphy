package netgloo.comands;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by mazi on 26.04.17.
 */
public class AdressenCommand {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String vornameVA;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String nachnameVA;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String emailVA;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String telVA;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String strasseVA;
    @NotEmpty
    @Size(min = 2, max = 70)
    private String ortVA;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String landVA;
    @NotEmpty
    @Size(min = 2, max = 4)
    private String plzVA;


    @Size(min = 2, max = 50)
    private String vornameRA;

    @Size(min = 2, max = 50)
    private String nachnameRA;

    @Size(min = 2, max = 50)
    private String emailRA;

    @Size(min = 2, max = 50)
    private String telRA;

    @Size(min = 2, max = 50)
    private String strasseRA;

    @Size(min = 2, max = 70)
    private String ortRA;

    @Size(min = 2, max = 50)
    private String landRA;

    @Size(min = 2, max = 4)
    private String plzRA;


    public AdressenCommand(String vornameVA, String nachnameVA, String emailVA, String telVA, String strasseVA, String ortVA, String landVA, String plzVA, String vornameRA, String nachnameRA, String emailRA, String telRA, String strasseRA, String ortRA, String landRA, String plzRA) {
        this.vornameVA = vornameVA;
        this.nachnameVA = nachnameVA;
        this.emailVA = emailVA;
        this.telVA = telVA;
        this.strasseVA = strasseVA;
        this.ortVA = ortVA;
        this.landVA = landVA;
        this.plzVA = plzVA;
        this.vornameRA = vornameRA;
        this.nachnameRA = nachnameRA;
        this.emailRA = emailRA;
        this.telRA = telRA;
        this.strasseRA = strasseRA;
        this.ortRA = ortRA;
        this.landRA = landRA;
        this.plzRA = plzRA;
    }


    public AdressenCommand() {

    }

    public String getVornameVA() {
        return vornameVA;
    }

    public void setVornameVA(String vornameVA) {
        this.vornameVA = vornameVA;
    }

    public String getNachnameVA() {
        return nachnameVA;
    }

    public void setNachnameVA(String nachnameVA) {
        this.nachnameVA = nachnameVA;
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

    public String getVornameRA() {
        return vornameRA;
    }

    public void setVornameRA(String vornameRA) {
        this.vornameRA = vornameRA;
    }

    public String getNachnameRA() {
        return nachnameRA;
    }

    public void setNachnameRA(String nachnameRA) {
        this.nachnameRA = nachnameRA;
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
}
