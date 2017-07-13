package netgloo.models.daos;

import netgloo.models.Websitecounter;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 13.07.17.
 */
public interface WebsiteCounterDao extends CrudRepository<Websitecounter,Long> {

}
