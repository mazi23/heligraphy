package netgloo.controllers;

import com.mpay24.payment.Mpay24;
import com.mpay24.payment.PaymentException;
import com.mpay24.payment.data.Payment;
import com.mpay24.payment.data.PaymentRequest;
import com.mpay24.payment.type.CreditCardPaymentType;
import com.mpay24.payment.type.PaymentTypeData;
import net.sf.jasperreports.engine.JRException;
import netgloo.Application;
import netgloo.models.Code;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.daos.BestellungDao;
import netgloo.models.daos.BildDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;


/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class indexController {

    @Autowired
    BildDao bildDao;

    @Autowired
    ShoppingCart shoppingChart;



    final String username = "info@heligraphy.at";
    final String password = "it#Ychuri6";
    Properties props;

    Session session ;


    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @RequestMapping({"/", "/index",""})
    public String getIndex(Model model) throws JRException {

        //model.addAttribute("products", null);
        model.addAttribute("suchcode", new Code());
        //generateAbrechnungsReport();
        //generateReport();


        return "index";
    }



    @RequestMapping(value = "/suchen", method = RequestMethod.POST)
    public String sendCodeToGridView(@RequestParam(value = "code")String code, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("code",code);
        return "redirect:picture-grid";
    }





/*    public void generateAbrechnungsReport() throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        jasperReport = JasperCompileManager
                .compileReport("src/main/resources/static/jasper/Simple_Blue.jrxml");

        LinkedList<Abrechnung> ls = new LinkedList<>();
        for (int i = 1; i<4;i++){
            Abrechnung a = new Abrechnung();
            a.setAnteil(10.4*i);
            a.setKaufdatum(new Date());
            a.setPreis(20.53*i);
            ls.add(a);
        }



        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ls);
        parameter.put("AbrechnungDataSet", itemsJRBean);
        parameter.put("Fotograf","Test");
        parameter.put("Summe",30.32);



        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        //return JasperExportManager.exportReportToPdf(jasperPrint);

        JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example1.pdf");

    }

    public void generateReport() throws JRException {


        Bestellung bs = new Bestellung();


        Adresse versandadresse = new Adresse();
        versandadresse.setLand("Österreich");
        versandadresse.setOrt("Gallspach");
        versandadresse.setAnschrift("Niederndorf 7");
        versandadresse.setPlz(Integer.parseInt("1234"));


        bs.setLieferAdresse(versandadresse);


            //Versandadresse ist gleich Rechnungsadresse
            bs.setRechnungsAdresse(versandadresse);





            BestellElement element = new BestellElement();
            element.setPreis(10.4);
            element.setBezeichnung("test");
            element.setBildID(1);
            element.setStueck(1);

            bs.addBestellElement(element);


        BestellElement element2 = new BestellElement();
        element2.setPreis(20.4);
        element2.setBezeichnung("Bild2");
        element2.setBildID(2);
        element2.setStueck(2);

        bs.addBestellElement(element2);

        BestellElement element3 = new BestellElement();
        element3.setPreis(20.4);
        element3.setBezeichnung("Bild23");
        element3.setBildID(2);
        element3.setStueck(2);

        bs.addBestellElement(element3);



        bs.setAuftragsDatum(new Timestamp(System.currentTimeMillis()));
        bs.setBildgruppenID(Integer.parseInt("1"));
        bs.setMwst(20);
        bs.setSummebrutto(100);
        bs.setVersankosten(5);
        bs.setVersandart("Vorrauskassa");
        bs.setSummenetto(100/1.2);
        bs.setSummemwst(10);
        bs.setIdBestellung(10);
        User kunde = null;
        if (kunde == null) {
            kunde = new User();

            kunde.setAdresse(versandadresse);
            kunde.setTelefon("06573992343");
            kunde.setVorname("Franz");
            kunde.setNachname("huber");
            kunde.setEmail("irgendetwas@ggm.cd");
            kunde.setRole(Role.USER);
            kunde.addBestellung(bs);
            kunde.setIdBildgruppe(Integer.parseInt("2"));
            kunde.setEnabled(true);

        }else{
            kunde.addBestellung(bs);
        }



        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        jasperReport = JasperCompileManager
                .compileReport("src/main/resources/static/jasper/Invoice.jrxml");




        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(bs.getBilder());
        parameter.put("ItemDataSource", itemsJRBean);

        parameter.put("summenetto",bs.getSummenetto());
        parameter.put("summebrutto",(bs.getSummebrutto()*100)/100000);
        parameter.put("summemwst",bs.getSummemwst());
        parameter.put("auftragsDatum",bs.getAuftragsDatum());
        parameter.put("idBestellung",bs.getIdBestellung());
        parameter.put("RAName",kunde.getVorname() + " " + kunde.getNachname());
        parameter.put("RAStrasse", bs.getRechnungsAdresse().getAnschrift());
        parameter.put("RAOrt",bs.getRechnungsAdresse().getPlz() + " " + bs.getRechnungsAdresse().getOrt());
        parameter.put("RALand",bs.getRechnungsAdresse().getLand());

        parameter.put("VAName",kunde.getVorname() + " " + kunde.getNachname());
        parameter.put("VAStrasse", bs.getLieferAdresse().getAnschrift());
        parameter.put("VAOrt", bs.getLieferAdresse().getPlz() + " " + bs.getLieferAdresse().getOrt());
        parameter.put("VALand", bs.getLieferAdresse().getLand());


        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        //return JasperExportManager.exportReportToPdf(jasperPrint);

        JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example1.pdf");



    }

*/
}
