package netgloo.models.daos;

import netgloo.models.Fotograf;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mazi on 03.05.17.
 */
@Transactional
public interface FotografDao extends CrudRepository<Fotograf,Long> {

}
