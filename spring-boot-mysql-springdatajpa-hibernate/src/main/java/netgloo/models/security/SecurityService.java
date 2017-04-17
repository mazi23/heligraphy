package netgloo.models.security;

/**
 * Created by mazi on 31.01.17.
 */
public interface SecurityService
{

        String findLoggedInUsername();

        void autologin(String username, String password);

}
