package netgloo.controllers;


import netgloo.models.Adresse;
import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.UploaderObject;
import netgloo.models.daos.AdresseDao;
import netgloo.models.daos.BildDao;
import netgloo.models.daos.BildgruppeDao;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by mazi on 21.01.17.
 */

@Controller
public class UploaderController {

    @Autowired
    BildDao bildDao;
    @Autowired
    AdresseDao adresseDao;
    @Autowired
    BildgruppeDao bildgruppeDao;

    HashMap<Integer, BackendBild> bilder = new HashMap<Integer, BackendBild>();

    @RequestMapping(value = "upload")
    public String start(Model model) {
        bilder=null;
        model.addAttribute("uploadObject", new UploaderObject());


        List<Objects[]> list = bildDao.findBild(); //hibernateUtil.dataRequest("SELECT   b.idBild, b.bildgruppeId, b.thumbnail FROM Bild b");

        bilder =  new HashMap<Integer, BackendBild>();

        for (Object[] o:list) {
            if(o[0]!=null){
                BackendBild bild = new BackendBild();
                bild.setBild((byte[]) o[2]);
                bild.setBildid( Math.toIntExact((Long) o[0]));
                bild.setId(Math.toIntExact((Long) o[1]));
                model.addAttribute("image_id",Math.toIntExact((Long) o[0]));
                bilder.put(Math.toIntExact((Long) o[0]),bild);
            }
        }

        model.addAttribute("Bilder",bilder);

        return "BackendBilderUpload";
    }

    @RequestMapping(value="/upload/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getQRImage(@PathVariable final String id) {
        BackendBild b = bilder.get(Integer.parseInt(id));
        return b.getBild();
    }


    @RequestMapping(value = "loadBilder", method = RequestMethod.POST)
    public String  test(@Valid UploaderObject object) throws IOException {


        Adresse adresse = new Adresse();
        adresse.setAnschrift(object.getAdresse());
        adresse.setLand(object.getLand());
        adresse.setOrt(object.getOrt());
        adresse.setPlz(Integer.parseInt(object.getPlz()));
        adresseDao.save(adresse);
        //hibernateUtil.createEntity(adresse);
        Bildgruppe bildgruppe = new Bildgruppe();
        bildgruppe.setAdresse(adresse);
        bildgruppeDao.save(bildgruppe);


        for (MultipartFile f:object.getBilder()) {
            Bild bild = new Bild();
            BufferedImage thumbnail = ImageIO.read(new ByteArrayInputStream(f.getBytes()));
            bild.setBildgruppe(bildgruppe);
            bild.setDatei(f.getBytes());
            bild.setErzeuger(object.getFotograf());
            BufferedImage scaledImg = Scalr.resize(thumbnail, 150);
            scaledImg = Scalr.rotate(scaledImg,Scalr.Rotation.CW_180,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);

            bild.setThumbnail(baos.toByteArray());

            bildDao.save(bild);




        }
        return "BackendBilderUpload";
    }





}
