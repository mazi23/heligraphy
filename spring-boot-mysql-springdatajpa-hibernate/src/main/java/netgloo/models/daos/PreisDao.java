package netgloo.models.daos;

import netgloo.models.Preis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mazi on 17.04.17.
 */
@Transactional
public interface PreisDao extends CrudRepository<Preis,Long> {

    
}
