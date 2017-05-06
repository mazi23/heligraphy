package netgloo.models.daos;

import netgloo.models.Bildgruppe;
import netgloo.models.Preis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mazi on 17.04.17.
 */
@Transactional
public interface PreisDao extends CrudRepository<Preis,Long> {

    @Query(" from Preis p where preis=:prize")
    Preis findByPreis(@Param("prize") double prize);
}
