package netgloo.models.daos;

import netgloo.models.Adresse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mazi on 29.01.17.
 */

public interface AdresseDao extends CrudRepository<Adresse, Long> {
    Adresse findByid(long id);

    @Query("FROM Adresse a where a.anschrift= :anschrift")
    List<Adresse> AdresseByAnschrift(@Param("anschrift") String anschrift);

}
