package netgloo.controllers;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.comands.Abrechnungsid;
import netgloo.models.*;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.UploaderObject;
import netgloo.models.daos.*;
import netgloo.models.reportObjects.Abrechnung;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    FotografDao fotografDao;

    @Autowired
    FotografAbrechnungDao fotografAbrechnungDao;

    HashMap<Integer, BackendBild> bilder = new HashMap<Integer, BackendBild>();

    @RequestMapping(value = "upload")
    public String start(Model model) {
        bilder=null;
        model.addAttribute("uploadObject", new UploaderObject());


        List<Bild> list = bildDao.findBild(); //hibernateUtil.dataRequest("SELECT   b.idBild, b.bildgruppeId, b.thumbnail FROM Bild b");
        Set<Long> bildgruppenids = new HashSet<>();
        bilder =  new HashMap<Integer, BackendBild>();

            for (Bild o:list) {
            if(o.getDatei()!=null){
                BackendBild bild = new BackendBild();
                bild.setBild(o.getThumbnail());
                bild.setBildid( Math.toIntExact(o.getId()));
                bildgruppenids.add(o.getBildgruppe().getId());
                bild.setId(Math.toIntExact(o.getBildgruppe().getId()));
                bild.setErzeuger(o.getFotograf().getName().toString());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",Math.toIntExact(o.getId()));
                bilder.put(Math.toIntExact(o.getId()),bild);
            }//b.id, b.bildgruppe.id, b.thumbnail, b.erzeuger, b.preis
        }

        model.addAttribute("fotografen",fotografDao.findAll());
        model.addAttribute("Bilder",bilder);
        model.addAttribute("ids", bildgruppenids);
        model.addAttribute("fotografid", new Abrechnungsid());

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
        Fotograf fotograf = fotografDao.findOne(Long.parseLong(object.getFotograf()));
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
            bild.setFotograf(fotograf);
            //bild.setPreis(p);
            BufferedImage scaledImg = Scalr.resize(thumbnail, 200);
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
                bild.setBild(o.getThumbnail());
                bild.setBildid( Math.toIntExact(o.getId()));
                bild.setId(Math.toIntExact(o.getBildgruppe().getId()));
                bild.setErzeuger(o.getFotograf().getName().toString());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",Math.toIntExact(o.getId()));
                bilder.put(Math.toIntExact(o.getId()),bild);
            }//b.id, b.bildgruppe.id, b.thumbnail, b.erzeuger, b.preis
        }

        model.addAttribute("Bilder",bilder);

          return "redirect:upload";
    }


    @RequestMapping(value = "/bildgruppel/{id}", method = RequestMethod.GET)
    public String deleteBilder(Model model, @PathVariable final String id){


        Bildgruppe bildgruppe = bildgruppeDao.findOne(Long.parseLong(id));
         bildDao.deleteByBildgruppe(bildgruppe);
        bildgruppeDao.delete(Long.parseLong(id));
        return "redirect:/upload";
    }

    @RequestMapping(value = "/upload/abrechnung/")
    public String abrechnung(Model model, @ModelAttribute Abrechnungsid id, RedirectAttributes redirectAttributes) throws JRException {
        Fotograf f = fotografDao.findOne(id.getId());
        List<FotografAbrechnung>fotografAbrechnung = fotografAbrechnungDao.findByFotograf(f);
        byte[] test = generateAbrechnungsReport(fotografAbrechnung);
        redirectAttributes.addFlashAttribute("report",generateAbrechnungsReport(fotografAbrechnung));
        return "redirect:/PDFAbrechnung";
    }



    public byte[] generateAbrechnungsReport(List<FotografAbrechnung> abrechnungen) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        jasperReport = JasperCompileManager
                .compileReport("src/main/resources/static/jasper/Simple_Blue.jrxml");
            double summe = 0.0;
        LinkedList<Abrechnung> ls = new LinkedList<>();
        for (FotografAbrechnung fa:abrechnungen){
            Abrechnung a = new Abrechnung();
            a.setAnteil(fa.getSumme());
            summe += fa.getSumme();
            a.setKaufdatum(fa.getGekauftwann());
            a.setPreis(fa.getPreis().getPreis());
            fa.setAbgerechnet(true);
            fa.setAbrechnungsDatum(new Date());
            ls.add(a);
        }




        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ls);
        parameter.put("AbrechnungDataSet", itemsJRBean);
        parameter.put("Fotograf",abrechnungen.get(0).getFotograf().getName());
        parameter.put("Summe",summe);



        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());


        //JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example1.pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }
}
