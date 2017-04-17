package netgloo.models.daos;

import netgloo.models.Bestellung;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 29.01.17.
 */
public interface BestellungDao extends CrudRepository<Bestellung,Long> {

    //public int findByBildgruppeid(long id);
}
