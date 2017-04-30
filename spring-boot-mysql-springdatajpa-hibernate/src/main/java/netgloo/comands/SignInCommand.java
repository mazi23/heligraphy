package netgloo.comands;

import netgloo.models.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

;

/**
 * Created by mazi on 20.04.17.
 */
public class SignInCommand {

    @NotNull
    @Size(min=2, max=30)
    private String email;
    @NotNull
    @Size(min=2, max=30)
    private String nachname;
    private String titel;
    @NotNull
    @Size(min=2, max=30)
    private String vorname;

    private boolean enabled;

    private String telefon;

 
    private String art;

    @NotNull
    @Size(min=2, max=30)
    private String anschrift;
    @NotNull
    @Size(min=2, max=30)
    private String Ort;

    private String land;
    @NotNull
    @Size(min=2, max=30)
    private String plz;
    private Integer idBildgruppe;
    @NotNull
    @Size(min=2, max=30)
    private String username;
    @NotNull
    @Size(min=2, max=30)
    private String passwort;

    private Role role = Role.USER;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
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

    public Integer getIdBildgruppe() {
        return idBildgruppe;
    }

    public void setIdBildgruppe(Integer idBildgruppe) {
        this.idBildgruppe = idBildgruppe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
