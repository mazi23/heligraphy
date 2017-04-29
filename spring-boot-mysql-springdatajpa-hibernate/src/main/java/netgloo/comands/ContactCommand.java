package netgloo.comands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by mazi on 21.04.17.
 */
public class ContactCommand {

    @NotNull
    @Size(min=2, max=30)
    String name;

    @NotNull
    @Size(min=2, max=30)
    String mail;

    @NotNull
    @Size(min=2, max=30)
    String betreff;

    String telefon;

    @NotNull
    @Size(min=20)
    String nachricht;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBetreff() {
        return betreff;
    }

    public void setBetreff(String betreff) {
        this.betreff = betreff;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }
}
