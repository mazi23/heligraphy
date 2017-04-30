package netgloo.controllers;


import netgloo.models.Adresse;
import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.UploaderObject;
import netgloo.models.daos.AdresseDao;
import netgloo.models.daos.BildDao;
import netgloo.models.daos.BildgruppeDao;
import netgloo.models.daos.PreisDao;
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
    @Autowired
    PreisDao preisDao;

    HashMap<Integer, BackendBild> bilder = new HashMap<Integer, BackendBild>();

    @RequestMapping(value = "upload")
    public String start(Model model) {
        bilder=null;
        model.addAttribute("uploadObject", new UploaderObject());


        List<Bild> list = bildDao.findBild(); //hibernateUtil.dataRequest("SELECT   b.idBild, b.bildgruppeId, b.thumbnail FROM Bild b");

        bilder =  new HashMap<Integer, BackendBild>();

            for (Bild o:list) {
            if(o.getDatei()!=null){
                BackendBild bild = new BackendBild();
                bild.setBild((byte[]) o.getThumbnail());
                bild.setBildid( Math.toIntExact((Long) o.getId()));
                bild.setId(Math.toIntExact((Long) o.getBildgruppe().getId()));
                bild.setErzeuger(o.getErzeuger().toString());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",Math.toIntExact((Long) o.getId()));
                bilder.put(Math.toIntExact((Long) o.getId()),bild);
            }//b.id, b.bildgruppe.id, b.thumbnail, b.erzeuger, b.preis
        }

        model.addAttribute("Bilder",bilder);

        return "upload";
    }

    @RequestMapping(value="/upload/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getQRImage(@PathVariable final String id) {
        BackendBild b = bilder.get(Integer.parseInt(id));
        return b.getBild();
    }


    @RequestMapping(value = "loadBilder", method = RequestMethod.POST)
    public String test(@Valid UploaderObject object,Model model) throws IOException {


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
        /*Preis p = new Preis();
        p.setMwst(20);
        p.setPreis(object.getPreis());
        preisDao.save(p);
*/
        for (MultipartFile f:object.getBilder()) {
            Bild bild = new Bild();
            BufferedImage thumbnail = ImageIO.read(new ByteArrayInputStream(f.getBytes()));
            bild.setBildgruppe(bildgruppe);
            bild.setDatei(f.getBytes());
            bild.setErzeuger(object.getFotograf());
            //bild.setPreis(p);
            BufferedImage scaledImg = Scalr.resize(thumbnail, 150);
            //scaledImg = Scalr.rotate(scaledImg,Scalr.Rotation.CW_180,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);

            bild.setThumbnail(baos.toByteArray());

            bildDao.save(bild);
        }
        model.addAttribute("uploadObject", new UploaderObject());
        return "redirect:upload";
    }



    @RequestMapping(value = "/upload/delet/{id}", method = RequestMethod.GET)
    public String deletBild(Model model,@PathVariable final String id){
        bildDao.delete(Long.parseLong(id));
        model.addAttribute("uploadObject", new UploaderObject());
        List<Bild> list = bildDao.findBild(); //hibernateUtil.dataRequest("SELECT   b.idBild, b.bildgruppeId, b.thumbnail FROM Bild b");

        bilder =  new HashMap<Integer, BackendBild>();

        for (Bild o:list) {
            if(o.getDatei()!=null){
                BackendBild bild = new BackendBild();
                bild.setBild((byte[]) o.getThumbnail());
                bild.setBildid( Math.toIntExact((Long) o.getId()));
                bild.setId(Math.toIntExact((Long) o.getBildgruppe().getId()));
                bild.setErzeuger(o.getErzeuger().toString());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",Math.toIntExact((Long) o.getId()));
                bilder.put(Math.toIntExact((Long) o.getId()),bild);
            }//b.id, b.bildgruppe.id, b.thumbnail, b.erzeuger, b.preis
        }

        model.addAttribute("Bilder",bilder);

          return "redirect:upload";
    }



}
