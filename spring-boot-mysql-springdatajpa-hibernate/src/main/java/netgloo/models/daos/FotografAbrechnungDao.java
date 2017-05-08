package netgloo.models.daos;

import netgloo.models.FotografAbrechnung;
import netgloo.models.Preis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mazi on 06.05.17.
 */
public interface FotografAbrechnungDao extends CrudRepository<FotografAbrechnung,Long> {

    @Query(" from FotografAbrechnung f where fotograf=:fotograf")
    FotografAbrechnung findByFotograf(@Param("fotograf") FotografAbrechnung fotograf);

}
