package netgloo.models.daos;

import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import netgloo.models.DisplayObjects.PictureGridObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by mazi on 29.01.17.
 */
public interface BildDao extends CrudRepository<Bild,Long>  {


    //String  stmt = "Select g.id From Bildgruppe g  where uniqCode='"+code+"'";
    @Query(" from Bildgruppe g where uniqCode=:id")
    Bildgruppe findByid(@Param("id") String id);



    @Query("FROM Bild b")
    List<Bild> findBild();

    @Query("SELECT b.id,b.thumbnail,bildgruppe from Bild b where bildgruppe=:bgruppe")
    List<Objects[]> findBilder(@Param("bgruppe") Bildgruppe bgruppe);


    @Query("from Bild b where bildgruppe=:bgruppe")
    List<Bild> findBildermitgruppe(@Param("bgruppe") Bildgruppe bgruppe);

    @Query("Select b.id, b.thumbnail from Bild b where bildgruppe=:bgruppe")
    List<Object[]> findThumbnailbyBildgruppe(@Param("bgruppe") Bildgruppe bgruppe);


    @Query(" from Bild b where id=:id")
    Bild findBildByid(@Param("id") Long id);

    @Transactional
    Long deleteByBildgruppe(Bildgruppe bildgruppe);

}
