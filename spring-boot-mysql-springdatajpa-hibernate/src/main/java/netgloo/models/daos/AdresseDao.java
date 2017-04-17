package netgloo.models.daos;

import netgloo.models.Adresse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mazi on 29.01.17.
 */
@Transactional
public interface AdresseDao extends CrudRepository<Adresse, Long> {
    public Adresse findByid(long id);



}
