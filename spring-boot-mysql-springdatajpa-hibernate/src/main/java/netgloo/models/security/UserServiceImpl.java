package netgloo.models.security;

import netgloo.models.User;
import netgloo.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by mazi on 31.01.17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {/*

        user.setPasswort(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<UserRole> roles = new HashSet<>();
        for (UserRole role: roleRepository.findAll()
             ) {
            roles.add(role);
        }
        user.setUserRoles(roles);*/
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}