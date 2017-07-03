package netgloo.controllers;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.StringValue;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.makernotes.SonyType1MakernoteDirectory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.Application;
import netgloo.comands.Abrechnungsid;
import netgloo.models.*;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.UploaderObject;
import netgloo.models.daos.*;
import netgloo.models.reportObjects.Abrechnung;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.InputStream;
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


    String errors = "Folgende Bilder enthalten eine falsch formatierte Adresse: ";

    HashMap<Integer, BackendBild> bilder = new HashMap<Integer, BackendBild>();
    long von = 1;
    long bis = 50;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @RequestMapping(value = "upload")
    public String start(Model model) {
        //model.addAttribute("obs",new Object());
        bilder=null;
        model.addAttribute("uploadObject", new UploaderObject());
        List<Objects[]> list = bildDao.BilderOhneDatei(von,bis);
        Set<Long> bildgruppenids = new HashSet<>();
        bilder =  new HashMap<Integer, BackendBild>();

        for (Object[] o:list) {
            if(o[0]!=null){
               BackendBild bild = new BackendBild();
                bild.setBild((byte[]) o[1]);
                bild.setBildid( Math.toIntExact((Long) o[0]));
                bildgruppenids.add(((Bildgruppe)o[2]).getId());
                bild.setId(Math.toIntExact(((Bildgruppe)o[2]).getId()));
                bild.setBildgruppe((String) o[3]);
                bild.setErzeuger(((Fotograf)o[4]).getName());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",o[0]);
                bilder.put(Math.toIntExact((Long) o[0]),bild);
            }
        }

        model.addAttribute("errors",errors);
        model.addAttribute("fotografen",fotografDao.findAll());
        model.addAttribute("Bilder",bilder);
        model.addAttribute("ids", bildgruppenids);
        model.addAttribute("fotografid", new Abrechnungsid());

        return "upload";
    }



    @RequestMapping(value="/upload/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImage(@PathVariable final String id) {
        BackendBild b = bilder.get(Integer.parseInt(id));
        return b.getBild();
    }


    @RequestMapping(value = "loadBilder", method = RequestMethod.POST)
    public String upload(UploaderObject object,Model model) throws IOException, ImageProcessingException {


        Adresse adresse = new Adresse();
        Bildgruppe bildgruppe = new Bildgruppe();

        boolean getAddresseVonBild = false;
        //wenn die Felder leer sind, sollen die Daten aus dem Exif geholt werden
        if(object.getAdresse().equals("")&&object.getOrt().equals("")){
            getAddresseVonBild=true;
        }else {
            adresse.setAnschrift(object.getAdresse());
            adresse.setLand("Österreich");//object.getLand());
            adresse.setOrt(object.getOrt());
            adresse.setPlz(Integer.parseInt(object.getPlz()));
            adresseDao.save(adresse);
            bildgruppe.setAdresse(adresse);
            bildgruppeDao.save(bildgruppe);
        }

        Fotograf fotograf = fotografDao.findOne(Long.parseLong(object.getFotograf()));

        //überprüft ob die vorherige Adresse der aktuellen Adresse entspricht wenn nicht gleich dann ist ein neues Bild
        Adresse neueAdresse = new Adresse();
        neueAdresse.setAnschrift("");
        for (MultipartFile f:object.getBilder()) {

            Bild bild = new Bild();
           try{
               bild.setBildname(f.getOriginalFilename());

            BufferedImage thumbnail = ImageIO.read(new ByteArrayInputStream(f.getBytes()));
            bild.setDatei(f.getBytes());


            //exif
            ByteArrayInputStream stream = new ByteArrayInputStream(bild.getDatei());
            Metadata metadata = ImageMetadataReader.readMetadata(stream);
            String dt = "";
            if (getAddresseVonBild) {
                for (Directory dir : metadata.getDirectories()) {
                    for (Tag tag : dir.getTags()) {
                        if (tag.getTagName().equals("Artist")) {
                            dt = tag.getDescription();
                            break;
                        }
                    }
                    if(!dt.equals(""))break;
                }
                String[] ad = dt.split(";");
                if(ad.length>2) {
                    adresse.setAnschrift(ad[0]);
                    adresse.setLand("Österreich");
                    //System.out.println(bild.getBildname());
                    adresse.setOrt(ad[2].startsWith(" ") ? ad[2].substring(1) : ad[2]);
                    adresse.setPlz(Integer.parseInt(ad[1].replaceAll("\\s", "")));
                    if (ad.length == 4 && !ad[3].equals("")) {
                        adresse.setName(ad[3]);
                    } else {
                        adresse.setName("HausbewohnerIn");
                    }
                }else{
                    errors = errors+ bild.getBildname()+ ", ";
                    //System.out.println(bild.getBildname() + " enthält keine Adresse "+ad.length + "   " + ad[ad.length-1]);
                    continue;
                }


                if (!neueAdresse.getAnschrift().equals(adresse.getAnschrift())){
                    bildgruppe = new Bildgruppe();
                    neueAdresse=adresse;
                }

                List<Adresse> checkAdresse = adresseDao.AdresseByAnschrift(adresse.getAnschrift());
                if (checkAdresse.size()>0&&checkAdresse.get(0)!=null){
                    Bildgruppe bildgruppeVorhanden = bildgruppeDao.findBlidgruppeByAdresse(checkAdresse.get(0));
                    if (bildgruppeVorhanden!=null)bildgruppe=bildgruppeVorhanden;
                    bildgruppe.setAdresse(checkAdresse.get(0));
                }else {
                    adresseDao.save(adresse);
                    Bildgruppe bildgruppeVorhanden = bildgruppeDao.findBlidgruppeByAdresse(adresse);
                    if (bildgruppeVorhanden!=null)bildgruppe=bildgruppeVorhanden;
                    bildgruppe.setAdresse(adresse);
                }

                bildgruppeDao.save(bildgruppe);
            }

            //end exif
            bild.setBildgruppe(bildgruppe);
            bild.setFotograf(fotograf);

            BufferedImage scaledImg = Scalr.resize(thumbnail, 200);
            //scaledImg = Scalr.rotate(scaledImg,Scalr.Rotation.CW_180,null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);

            bild.setThumbnail(baos.toByteArray());
            adresse = new Adresse();
            bildDao.save(bild);
           }catch (Exception e){
               logger.error(bild.getBildname()+"---------------\n");
               logger.error(e.getMessage());
           }
        }
        model.addAttribute("errors",errors);
        model.addAttribute("uploadObject", new UploaderObject());
        return "redirect:upload";
    }


    @RequestMapping(value ="/nextSite",method = RequestMethod.GET)
    public String nextSite(Model model,@RequestParam(value = "von") long von,@RequestParam(value = "bis") long bis){
        this.von = von;
        this.bis = bis;
        return "redirect:upload";
    }

    @RequestMapping(value = "/upload/delet/{id}", method = RequestMethod.GET)
    public String deletBild(Model model,@PathVariable final String id){
        bildDao.delete(Long.parseLong(id));
        model.addAttribute("uploadObject", new UploaderObject());
        List<Objects[]> list = bildDao.BilderOhneDatei(von,bis);
        Set<Long> bildgruppenids = new HashSet<>();
        bilder =  new HashMap<Integer, BackendBild>();

        for (Object[] o:list) {
            if(o[0]!=null){
                BackendBild bild = new BackendBild();
                bild.setBild((byte[]) o[1]);
                bild.setBildid( Math.toIntExact((Long) o[0]));
                bildgruppenids.add(((Bildgruppe)o[2]).getId());
                bild.setId(Math.toIntExact(((Bildgruppe)o[2]).getId()));
                bild.setBildgruppe((String) o[3]);
                bild.setErzeuger(((Fotograf)o[4]).getName());
                //if(o.getPreis()!=null) bild.setPreis(o.getPreis().getPreis());
                model.addAttribute("image_id",o[0]);
                bilder.put(Math.toIntExact((Long) o[0]),bild);
            }
        }

        model.addAttribute("Bilder",bilder);

          return "redirect:/upload";
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
        //byte[] test = generateAbrechnungsReport(fotografAbrechnung);
        redirectAttributes.addFlashAttribute("report",generateAbrechnungsReport(fotografAbrechnung));
        return "redirect:/PDFAbrechnung";
    }



    public byte[] generateAbrechnungsReport(List<FotografAbrechnung> abrechnungen) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
       // jasperReport = JasperCompileManager.compileReport("src/main/resources/static/jasper/Simple_Blue.jrxml");

        InputStream in = getClass().getResourceAsStream("/static/jasper/Simple_Blue.jrxml");
        jasperReport = JasperCompileManager.compileReport(in);
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
