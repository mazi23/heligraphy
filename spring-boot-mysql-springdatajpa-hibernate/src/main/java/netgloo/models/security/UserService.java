package netgloo.models.security;

import netgloo.models.User;

/**
 * Created by mazi on 31.01.17.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
