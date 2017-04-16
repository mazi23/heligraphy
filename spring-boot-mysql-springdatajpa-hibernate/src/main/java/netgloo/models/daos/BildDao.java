package netgloo.models.daos;

import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

/**
 * Created by mazi on 29.01.17.
 */
public interface BildDao extends CrudRepository<Bild,Long>  {


    //String  stmt = "Select g.id From Bildgruppe g  where uniqCode='"+code+"'";
    @Query(" from Bildgruppe g where uniqCode=:id")
    public Bildgruppe findByid(@Param("id") String id);


    @Query("SELECT   b.id, b.bildgruppe.id, b.thumbnail FROM Bild b")
    public List<Objects[]> findBild();

    @Query("SELECT b.id,b.thumbnail,bildgruppe from Bild b where bildgruppe=:bgruppe")
    public List<Objects[]> findBilder(@Param("bgruppe") Bildgruppe bgruppe);


    @Query("from Bild b where bildgruppe=:bgruppe")
    public List<Bild> findBildermitgruppe(@Param("bgruppe") Bildgruppe bgruppe);
}
