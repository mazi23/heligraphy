package netgloo.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.Mail;
import netgloo.comands.AdressenCommand;
import netgloo.models.*;
import netgloo.models.DisplayObjects.OverviewPrice;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by mazi on 29.04.17.
 */
@Controller
public class overviewController {

    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    AdressenCommand adCommand;
    @Autowired
    BildDao bildDao;
    @Autowired
    AdresseDao adresseDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BestellungDao bestellungDao;
    @Autowired
    BestellElementDao bestellElementDao;


    User kunde;
    Bestellung bestellung;

    @RequestMapping("/overview")
    public String start (Model model, @ModelAttribute(value = "adressenCommand") AdressenCommand adressenCommand){


        OverviewPrice overviewPrice = new OverviewPrice();
        String zahlungsart = adressenCommand.getZahlungsart();
        if (zahlungsart==null) overviewPrice.setVersandkosten(5);
        else if (zahlungsart.equals("Nachname")) overviewPrice.setVersandkosten(12);
        else if (zahlungsart.equals("Vorrauskasse")) overviewPrice.setVersandkosten(5);





        bestellung = new Bestellung();

        kunde = userDao.findByEmail(adressenCommand.getEmailVA());
        Adresse versandadresse = new Adresse();
        versandadresse.setLand(adressenCommand.getLandVA());
        versandadresse.setOrt(adressenCommand.getOrtVA());
        versandadresse.setAnschrift(adressenCommand.getStrasseVA());
        versandadresse.setPlz(Integer.parseInt(adressenCommand.getPlzVA()));
        adresseDao.save(versandadresse);

        bestellung.setLieferAdresse(versandadresse);

        if (adressenCommand.getEmailRA().equals("") && adressenCommand.getNachnameRA().equals("")) {
            //Versandadresse ist gleich Rechnungsadresse
            bestellung.setRechnungsAdresse(versandadresse);
        } else {
            Adresse rechnungsAdresse = new Adresse();
            rechnungsAdresse.setLand(adressenCommand.getLandRA());
            rechnungsAdresse.setOrt(adressenCommand.getOrtRA());
            rechnungsAdresse.setAnschrift(adressenCommand.getStrasseRA());
            rechnungsAdresse.setPlz(Integer.parseInt(adressenCommand.getPlzRA()));
            adresseDao.save(rechnungsAdresse);
            bestellung.setRechnungsAdresse(rechnungsAdresse);
        }



        for(ShoppingCartItem item: shoppingCart.getItems()){
            BestellElement element = new BestellElement();
            element.setPreis(item.getPrice());
            element.setBezeichnung("test");
            element.setBildID(item.getId());
            element.setStueck(item.getQuantity());
            bestellElementDao.save(element);
            bestellung.addBestellElement(element);
            overviewPrice.setZwischenSumme(overviewPrice.getZwischenSumme()+(item.getPrice()*item.getQuantity()));
            System.out.println(item.getQuantity());
        }



        overviewPrice.setGesamtkosten(overviewPrice.getZwischenSumme()+overviewPrice.getVersandkosten());
        bestellung.setAuftragsDatum(new Timestamp(System.currentTimeMillis()));
        bestellung.setBildgruppenID(Integer.parseInt(shoppingCart.getBildgruppe()));
        bestellung.setMwst(20);
        bestellung.setSummebrutto(overviewPrice.getGesamtkosten());
        bestellung.setVersankosten(overviewPrice.getVersandkosten());
        bestellung.setVersandart(zahlungsart);
        bestellung.setSummenetto(overviewPrice.getGesamtkosten()/1.2);
        bestellung.setSummemwst(overviewPrice.getGesamtkosten());

        bestellungDao.save(bestellung);
        if (kunde == null) {
            kunde = new User();

            kunde.setAdresse(versandadresse);
            kunde.setTelefon(adressenCommand.getTelVA());
            kunde.setVorname(adressenCommand.getVornameVA());
            kunde.setNachname(adressenCommand.getNachnameVA());
            kunde.setEmail(adressenCommand.getEmailVA());
            kunde.setRole(Role.USER);
            kunde.addBestellung(bestellung);
            kunde.setIdBildgruppe(Integer.parseInt(shoppingCart.getBildgruppe()));
            kunde.setEnabled(true);
            userDao.save(kunde);
        }else{
            kunde.addBestellung(bestellung);
        }
        bestellung.setuser(kunde);
        bestellungDao.save(bestellung);


        model.addAttribute("overviewPrice",overviewPrice);
        model.addAttribute("Items",shoppingCart.getItems());
        return "overview";
    }

    @RequestMapping(value="/overview/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return bildDao.findBildByid(Long.parseLong(id)).getThumbnail();
    }

    @RequestMapping(value = "/weitereinkaufen")
    public String weiterEinkaufen(Model model,RedirectAttributes redirectAttributes){
        System.out.println(shoppingCart.getBildgruppe());
        redirectAttributes.addFlashAttribute("code",shoppingCart.getBildgruppe());
        return "redirect:picture-grid";
    }

    @RequestMapping(value = "/BestellungAbsenden")
    public String bestellungAbsenden(Model model) throws IOException, MessagingException, JRException {
        Mail mailToPrint = new Mail();


        //mailToPrint.setAbsenderMail("info@heligraphy.at");
        //ToDO: email auf Druckerei ändern
        mailToPrint.setDruckereiMail("mac.matthias@gmail.com");
        HashMap<Integer,Bild> bilder = new HashMap();

        //
        String textprintPaper = "";
        String textprintLeinwand ="";
        for (ShoppingCartItem item: shoppingCart.getItems()){
            if(item.getPrice()==280||item.getPrice()==500) {
                bilder.put(item.getId(),bildDao.findBildByid((long) item.getId()));
                if(item.getPrice()==280) textprintPaper+= "image"+ item.getId()+".jpg,";
                if(item.getPrice()==500) textprintLeinwand+= "image" + item.getId()+".jpg,";
                //bilder.add(bildDao.findBildByid((long) item.getId()));
            }
        }
       if(!textprintPaper.equals("")){
            textprintPaper+= " als Lambda-Abzug auf\n" +
                   "Fuji Crystal DP II auf A3 Fuji Crystal Archive glänzend, randlos ohne Rahmen  ";
       }
       if (!textprintLeinwand.equals("")){
           textprintLeinwand += " als Leinwand auf Trägerrahmen, 90x60, Leinwandprint matt, 20mm Trägerrahmen, Motiv an Rand gespiegelt ";
       }

        String textPrint ="";
        if (bestellung.getVersandart().equals("Nachname")){
            textPrint= "Sehr geehrte Damen und Herren," +
                    "anbei finden sie die gewünschten Bilddateien zum Drucken. \r\n" +
                    "Gewünscht wird: " + textprintLeinwand +textprintPaper  + "\n Bitte mit der Versandart Nachname." +
                    "\r\n Rechnungsadresse: \r\n" +
                    "Matthias Oberegger \r\n" +
                    "Pfarrgraben 6 \r\n" +
                    "4713 Gallspach \r\n" +
                    "Österreich \r\n \r\n" +
                    "Versandadresse: \r\n " + kunde.getVorname() + " " + kunde.getNachname() + "\r\n" +
                    kunde.getAdresse().getAnschrift() +"\r\n" +
                    kunde.getAdresse().getPlz() + " " +kunde.getAdresse().getOrt()+ "\r\n" +
                    kunde.getAdresse().getLand()+"\r\n" +
                    "Mit freundlichen Grüßen \r\n" +
                    "Matthis Oberegger";
        }
        else {
            textPrint= "Sehr geehrte Damen und Herren," +
                    "anbei finden sie die gewünschten Bilddateien zum Drucken. \r\n" +
                    "Gewünscht wird: " + textprintLeinwand +textprintPaper  +
                    "\r\n\n Rechnungsadresse: \r\n" +
                    "Matthias Oberegger \r\n" +
                    "Pfarrgraben 6 \r\n" +
                    "4713 Gallspach \r\n" +
                    "Österreich \r\n \r\n" +
                    "Versandadresse: \r\n " + kunde.getVorname() + " " + kunde.getNachname() + "\r\n" +
                    kunde.getAdresse().getAnschrift() +"\r\n" +
                    kunde.getAdresse().getPlz() + " " +kunde.getAdresse().getOrt()+ "\r\n" +
                    kunde.getAdresse().getLand()+"\r\n" +
                    "Mit freundlichen Grüßen \r\n" +
                    "Matthis Oberegger";
        }



        mailToPrint.sendMail("HeliGrapyh Druckanfrage",textPrint,bilder);



        String textCustomer = "Sehr geehrte(r) Frau/Herr " + kunde.getNachname() +", \r\n \r\n "+
                "wir haben Ihre Bestellung erhalten und geben die Bilder an unsere Druckerei weiter." +
                "Sollten Sie die Zahlungsweiße Vorrauskassa gewählt haben, so bitten wir Sie den Betrag aus der Rechnung umgehend zu überweisen." +
                "\r\n Bitte beachten Sie auch, dass sobald wir die Bestellung an die Druckerei weitergeleitet haben keine Stornierung mehr möglich ist. ";


        Mail mailtoCustomer = new Mail();
        byte[]pdf = generateReport(bestellung);
        mailtoCustomer.sendMailWithBill(kunde.getEmail(),textCustomer,pdf);

    return "bestellungAbgeschlossen";
    }
    public byte[] generateReport(Bestellung bs) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        jasperReport = JasperCompileManager
                .compileReport("src/main/resources/static/jasper/Invoice.jrxml");




        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(bs.getBilder());
        parameter.put("ItemDataSource", itemsJRBean);

        parameter.put("summenetto",bs.getSummenetto());
        parameter.put("summebrutto",(bs.getSummebrutto()*100)/1000);
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
        return JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example4.pdf");


    }

    public void sendBestellung()
    {

    }

}
