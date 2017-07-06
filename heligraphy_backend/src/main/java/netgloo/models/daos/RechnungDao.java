package netgloo.models.daos;

import netgloo.models.Rechnung;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by mazi on 04.07.17.
 */
@Transactional
public interface RechnungDao extends CrudRepository<Rechnung,Long> {

}
