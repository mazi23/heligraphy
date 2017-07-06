package netgloo.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.comands.Rechnungscommand;
import netgloo.models.*;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mazi on 25.05.17.
 */
@Controller
public class billController {

    @Autowired
    BestellungDao bestellungDao;

    @Autowired
    AdresseDao adresseDao;
    @Autowired
    BestellElementDao bestellElementDao;
    @Autowired
    RechnungDao rechnungDao;
    @Autowired
    UserDao userDao;

    String vaname;
    String rename;

    @RequestMapping("/createRechnung")
    public String start(Model model){

        model.addAttribute("command",new Rechnungscommand());

        return "PDFAbrechnung";
    }

    @RequestMapping(value = "/neueRechnung")
    public String neueRechnung(Model model, @ModelAttribute("command") Rechnungscommand rechnungscommand, RedirectAttributes redirectAttributes) throws JRException {
        Bestellung bs = new Bestellung();
        Date date = new Date();
        bs.setAuftragsDatum(new Timestamp(date.getTime()));

        Adresse versandadresse = new Adresse();
        versandadresse.setAnschrift(rechnungscommand.getVersandadresse().getStrasseVA());
        versandadresse.setLand(rechnungscommand.getVersandadresse().getLandVA());
        versandadresse.setOrt(rechnungscommand.getVersandadresse().getOrtVA());
        versandadresse.setPlz(Integer.parseInt(rechnungscommand.getVersandadresse().getPlzVA()));

        adresseDao.save(versandadresse);


        Adresse rechnungsadresse = new Adresse();
        if(rechnungscommand.getRechnungsadresse().getStrasseRA()!=null&&rechnungscommand.getRechnungsadresse().getLandRA()!=null){
            rechnungsadresse.setAnschrift(rechnungscommand.getRechnungsadresse().getStrasseRA());
            rechnungsadresse.setLand(rechnungscommand.getRechnungsadresse().getLandRA());
            rechnungsadresse.setOrt(rechnungscommand.getRechnungsadresse().getOrtRA());
            rechnungsadresse.setPlz(Integer.parseInt(rechnungscommand.getRechnungsadresse().getPlzRA()));
        }else{
            rechnungsadresse=versandadresse;
        }


        adresseDao.save(rechnungsadresse);

        bs.setRechnungsAdresse(rechnungsadresse);
        bs.setLieferAdresse(versandadresse);

        bs.setVersankosten(rechnungscommand.getVersandkosten());
       // bs.setVersandart(rechnungscommand.getVersandart());
        bs.setMwst(20);

        vaname=rechnungscommand.getVaName();
        rename=rechnungscommand.getReName();

        double summe =0.0;
        for (ShoppingCartItem element:rechnungscommand.getPositionen()) {
            BestellElement be = new BestellElement();
            if (element.getPrice()!=0) {
                be.setBezeichnung(element.getBeschreibung());
                be.setBildID(element.getId());
                be.setPreis(element.getPrice());
                be.setStueck(element.getQuantity());
                bs.getBilder().add(be);
                summe = summe+element.getPrice()*element.getQuantity();
                bestellElementDao.save(be);
            }

        }
        summe+=bs.getVersankosten();
        bs.setSummenetto(summe/1.2);
        bs.setSummebrutto(summe);
        bs.setSummemwst(summe/6);
        bs.setVersankosten(rechnungscommand.getVersandkosten());

        bestellungDao.save(bs);

        //ToDO: Bestellung mit daten und Bestellelementen füllen aus rechnungscommand
        byte[]report =generateReport(bs);
        Rechnung rechnung = new Rechnung();
        try {
            Blob b = new SerialBlob(report);
            rechnung.setRechnung(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rechnungDao.save(rechnung);
        redirectAttributes.addFlashAttribute("report",report);
        return "redirect:/PDFAbrechnung";
    }



    //Rechnungerstellen
    public byte[] generateReport(Bestellung bs) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        //jasperReport = JasperCompileManager.compileReport("../resources/static/jasper/Invoice.jrxml");
        InputStream in = getClass().getResourceAsStream("/static/jasper/Invoice.jrxml");
        jasperReport = JasperCompileManager.compileReport(in);

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(bs.getBilder());
        parameter.put("ItemDataSource", itemsJRBean);

        parameter.put("summenetto", round2(bs.getSummenetto()));
        parameter.put("summebrutto", round2(bs.getSummebrutto()));
        parameter.put("summemwst", round2(bs.getSummemwst()));
        parameter.put("auftragsDatum", bs.getAuftragsDatum());
        parameter.put("idBestellung", bs.getIdBestellung());
        parameter.put("RAName", rename);
        parameter.put("RAStrasse", bs.getRechnungsAdresse().getAnschrift());
        parameter.put("RAOrt", bs.getRechnungsAdresse().getPlz() + " " + bs.getRechnungsAdresse().getOrt());
        parameter.put("RALand", bs.getRechnungsAdresse().getLand());
        parameter.put("Versandkosten",String.format("%10.2f", round2(bs.getVersankosten())));
        parameter.put("VAName", vaname);
        parameter.put("VAStrasse", bs.getLieferAdresse().getAnschrift());
        parameter.put("VAOrt", bs.getLieferAdresse().getPlz() + " " + bs.getLieferAdresse().getOrt());
        parameter.put("VALand", bs.getLieferAdresse().getLand());
        parameter.put("Versandart", "Vorauskasse");

        //parameter.put("kundennummer", String .valueOf(bs.getuser().getId()));

        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example4.pdf");


    }

    public Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
