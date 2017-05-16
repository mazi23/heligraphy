package netgloo.comands;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jt on 2/2/16.
 */
public class LoginCommand {

    //benutzername ist email
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String zahlungsart;

    public LoginCommand() {
    }

    public String getZahlungsart() {
        return zahlungsart;
    }

    public void setZahlungsart(String zahlungsart) {
        this.zahlungsart = zahlungsart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
