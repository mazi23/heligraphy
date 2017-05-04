package netgloo.models.daos;

import netgloo.models.Adresse;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 29.01.17.
 */

public interface AdresseDao extends CrudRepository<Adresse, Long> {
    Adresse findByid(long id);



}
