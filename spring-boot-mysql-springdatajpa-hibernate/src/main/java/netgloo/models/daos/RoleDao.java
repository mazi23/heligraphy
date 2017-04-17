package netgloo.models.daos;

import netgloo.models.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 31.01.17.
 */
public interface RoleDao extends CrudRepository <Role,Long> {

}
