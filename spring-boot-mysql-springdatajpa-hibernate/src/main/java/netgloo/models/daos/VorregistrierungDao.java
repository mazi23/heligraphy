package netgloo.models.daos;

import netgloo.models.Vorregistrierung;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by mazi on 19.07.17.
 */
@Service
public interface VorregistrierungDao extends CrudRepository<Vorregistrierung,Long> {

}
