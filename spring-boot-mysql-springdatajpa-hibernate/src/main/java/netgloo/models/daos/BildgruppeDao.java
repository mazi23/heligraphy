package netgloo.models.daos;

import netgloo.models.Adresse;
import netgloo.models.Bildgruppe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mazi on 29.01.17.
 */
public interface BildgruppeDao extends CrudRepository<Bildgruppe,Long> {

    //@Query(" from Bildgruppe g where uniqCode=:id")
    //public Bildgruppe findByid(@Param("id") String id);

    Bildgruppe findBlidgruppeByAdresse(Adresse adresse);
}
