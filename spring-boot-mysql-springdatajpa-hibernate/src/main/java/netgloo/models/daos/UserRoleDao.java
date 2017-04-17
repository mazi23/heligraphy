package netgloo.models.daos;

import netgloo.models.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 31.01.17.
 */
public interface UserRoleDao extends CrudRepository<UserRole,Long> {

}
