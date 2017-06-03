package netgloo.controllers;

import com.mpay24.payment.Mpay24;
import com.mpay24.payment.PaymentException;
import com.mpay24.payment.data.Payment;
import com.mpay24.payment.data.PaymentRequest;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.Application;
import netgloo.Mail;
import netgloo.comands.AdressenCommand;
import netgloo.models.*;
import netgloo.models.DisplayObjects.OverviewPrice;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.daos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;


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
    @Autowired
    PreisDao preisDao;
    @Autowired
    FotografDao fotografDao;

    @Autowired
    FotografAbrechnungDao abrechnungDao;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    Mpay24 mpay24 = new Mpay24("94894", "JoY?Mz8a9w", Mpay24.Environment.TEST);
    PaymentRequest paymentRequest = new PaymentRequest();
    User kunde;
    Bestellung bestellung;
    LinkedList<FotografAbrechnung> abrechnungen = new LinkedList<>();

    @RequestMapping("/overview")
    public String start(Model model, @ModelAttribute(value = "adressenCommand") AdressenCommand adressenCommand) {


        try {
            OverviewPrice overviewPrice = new OverviewPrice();
            String zahlungsart = adressenCommand.getZahlungsart();
            if (zahlungsart == null) overviewPrice.setVersandkosten(5);
            else if (zahlungsart.equals("Nachname")) overviewPrice.setVersandkosten(12);
            else if (zahlungsart.equals("Vorrauskasse")) overviewPrice.setVersandkosten(5);
            else if (zahlungsart.equals("Onlineueberweisung")) overviewPrice.setVersandkosten(0);


            bestellung = new Bestellung();

            kunde = userDao.findByEmail(adressenCommand.getEmailVA());
            Adresse versandadresse = new Adresse();
            versandadresse.setLand(adressenCommand.getLandVA());
            versandadresse.setOrt(adressenCommand.getOrtVA());
            versandadresse.setAnschrift(adressenCommand.getStrasseVA());
            versandadresse.setPlz(Integer.parseInt(adressenCommand.getPlzVA()));
            adresseDao.save(versandadresse);

            bestellung.setLieferAdresse(versandadresse);

            if (adressenCommand.getEmailRA().equals("") && adressenCommand.getNameRA().equals("")) {
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


            for (ShoppingCartItem item : shoppingCart.getItems()) {
                BestellElement element = new BestellElement();
                element.setPreis(item.getPrice());
                //Todo: beschreibung festlegen
                if (item.getPrice()== PreisPlan.BASIC.getValue()){
                    element.setBezeichnung("Bild A3 auf Fotopapier");
                }
                if (item.getPrice()== PreisPlan.STANDARD.getValue()){
                    element.setBezeichnung("Bild Download");
                }
                if (item.getPrice()== PreisPlan.PREMIUM.getValue()){
                    element.setBezeichnung("Bild auf Fotoleinwand 90x60");
                }
                if (item.getPrice()== PreisPlan.PROFESSIONAL.getValue()){
                    element.setBezeichnung("Bild Download und Bild A3 auf Fotopapier");
                }

                element.setBildID(item.getId());
                element.setStueck(item.getQuantity());
                bestellElementDao.save(element);
                bestellung.addBestellElement(element);
                overviewPrice.setZwischenSumme(overviewPrice.getZwischenSumme() + (item.getPrice() * item.getQuantity()));
                System.out.println(item.getQuantity());
            }


            overviewPrice.setGesamtkosten(overviewPrice.getZwischenSumme() + overviewPrice.getVersandkosten());
            bestellung.setAuftragsDatum(new Timestamp(System.currentTimeMillis()));
            bestellung.setBildgruppenID(Integer.parseInt(shoppingCart.getBildgruppe()));
            bestellung.setMwst(20);
            bestellung.setSummebrutto(overviewPrice.getGesamtkosten());
            bestellung.setVersankosten(overviewPrice.getVersandkosten());
            bestellung.setVersandart(zahlungsart);
            bestellung.setSummenetto(overviewPrice.getGesamtkosten() / 1.2);
            bestellung.setSummemwst(overviewPrice.getGesamtkosten() / 1.2 * 0.2);

            bestellungDao.save(bestellung);
            if (kunde == null) {
                kunde = new User();
                SecureRandom random = new SecureRandom();
                kunde.setAdresse(versandadresse);
                kunde.setTelefon(adressenCommand.getTelVA());
                kunde.setName(adressenCommand.getNameVA());

                kunde.setEmail(adressenCommand.getEmailVA());
                kunde.setRole(Role.USER);
                kunde.addBestellung(bestellung);
                kunde.setIdBildgruppe(Integer.parseInt(shoppingCart.getBildgruppe()));
                kunde.setEnabled(true);
                kunde.setPasswort(new BigInteger(50, random).toString(32));
                kunde.setUsername(kunde.getEmail());
                userDao.save(kunde);
                Mail mail = new Mail();
                String text = "Ihre Zugangsdaten lauten: \r\n Benutzer: " + kunde.getEmail() + "\r\n Passwort: " + kunde.getPasswort() + "\r\n Ihre Heligraphy Team";
                try {
                    mail.sendCustomMail(kunde.getEmail(), "Ihre Zugangsdaten", text);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } else {
                kunde.addBestellung(bestellung);
                     }
            bestellung.setuser(kunde);
            bestellungDao.save(bestellung);
            String urlid = "";
            for (ShoppingCartItem item : shoppingCart.getItems()) {

                urlid = bildDao.findCodeByid((long) item.getId()) + ","+urlid;
            }


            setPaymentRequest(new BigDecimal(bestellung.getSummebrutto()),Long.toString(bestellung.getIdBestellung()),urlid.substring(0,urlid.length()-1),kunde.getEmail());
            model.addAttribute("overviewPrice", overviewPrice);
            model.addAttribute("Items", shoppingCart.getItems());
            model.addAttribute("ausgewaehlteZahlungsart", adressenCommand.getZahlungsart());
        }catch (Exception e){
            logger.error("overview start -------\n" +e.getMessage());
        }
        return "overview";

    }

    @RequestMapping(value = "/overview/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return bildDao.findBildByid(Long.parseLong(id)).getThumbnail();
    }

    @RequestMapping(value = "/weitereinkaufen")
    public String weiterEinkaufen(Model model, RedirectAttributes redirectAttributes) {
        System.out.println(shoppingCart.getBildgruppe());
        redirectAttributes.addFlashAttribute("code", shoppingCart.getBildgruppe());
        return "redirect:picture-grid";
    }

    @RequestMapping(value = "/BestellungAbsenden")
    public String bestellungAbsenden(Model model) throws IOException, MessagingException, JRException {
        try {
            boolean druckerreiverstaendigen = false;
            Mail mailToPrint = new Mail();
            logger.info("-------------------------------------- \n Gesendet");

            //mailToPrint.setAbsenderMail("info@heligraphy.at");
            //ToDO: email auf Druckerei ändern
            mailToPrint.setDruckereiMail("mac.matthias@gmail.com");
            HashMap<Integer, Bild> bilder = new HashMap();

            //
            String textprintPaper = "";
            String textprintLeinwand = "";
            for (ShoppingCartItem item : shoppingCart.getItems()) {
                FotografAbrechnung abrechnung = new FotografAbrechnung();
                Bild aktBild = bildDao.findBildByid((long) item.getId());
                if (item.getPrice() == PreisPlan.BASIC.getValue() || item.getPrice() == PreisPlan.PREMIUM.getValue()) {
                    druckerreiverstaendigen=true;
                    abrechnung.setFotograf(aktBild.getFotograf());
                    abrechnung.setPreis(preisDao.findByPreis(item.getPrice()));
                    abrechnungen.add(abrechnung);
                    bilder.put(item.getId(), aktBild);
                    if (item.getPrice() == PreisPlan.BASIC.getValue())
                        textprintPaper += "image" + item.getId() + ".jpg,";
                    if (item.getPrice() == PreisPlan.PREMIUM.getValue())
                        textprintLeinwand += "image" + item.getId() + ".jpg,";
                    //bilder.add(bildDao.findBildByid((long) item.getId()));
                } else {
                    abrechnung.setFotograf(aktBild.getFotograf());
                    abrechnung.setPreis(preisDao.findByPreis(item.getPrice()));

                    //TODO: Download für Bilder

                }
            }
            if (!textprintPaper.equals("")) {
                textprintPaper += " als Lambda-Abzug auf\n" +
                        "Fuji Crystal DP II auf A3 Fuji Crystal Archive glänzend, randlos ohne Rahmen  ";
            }
            if (!textprintLeinwand.equals("")) {
                textprintLeinwand += " als Leinwand auf Trägerrahmen, 90x60, Leinwandprint matt, 20mm Trägerrahmen, Motiv an Rand gespiegelt ";
            }

            String textPrint = "";
            if (bestellung.getVersandart().equals("Nachname")) {
                textPrint = "Sehr geehrte Damen und Herren," +
                        "anbei finden sie die gewünschten Bilddateien zum Drucken. \r\n" +
                        "Gewünscht wird: " + textprintLeinwand + textprintPaper + "\n Bitte mit der Versandart Nachname." +
                        "\r\n Rechnungsadresse: \r\n" +
                        "Matthias Oberegger \r\n" +
                        "Pfarrgraben 6 \r\n" +
                        "4713 Gallspach \r\n" +
                        "Österreich \r\n \r\n" +
                        "Versandadresse: \r\n " + kunde.getName() + " \r\n" +
                        kunde.getAdresse().getAnschrift() + "\r\n" +
                        kunde.getAdresse().getPlz() + " " + kunde.getAdresse().getOrt() + "\r\n" +
                        kunde.getAdresse().getLand() + "\r\n" +
                        "Mit freundlichen Grüßen \r\n" +
                        "Matthis Oberegger";
            } else {
                textPrint = "Sehr geehrte Damen und Herren," +
                        "anbei finden sie die gewünschten Bilddateien zum Drucken. \r\n" +
                        "Gewünscht wird: " + textprintLeinwand + textprintPaper +
                        "\r\n\n Rechnungsadresse: \r\n" +
                        "Matthias Oberegger \r\n" +
                        "Pfarrgraben 6 \r\n" +
                        "4713 Gallspach \r\n" +
                        "Österreich \r\n \r\n" +
                        "\n" +
                        "Versandadresse: \r\n " + kunde.getName() + " \r\n" +
                        kunde.getAdresse().getAnschrift() + "\r\n" +
                        kunde.getAdresse().getPlz() + " " + kunde.getAdresse().getOrt() + "\r\n" +
                        kunde.getAdresse().getLand() + "\r\n" +
                        "Mit freundlichen Grüßen \r\n" +
                        "Matthis Oberegger";
            }

            if (bilder.size()>=1) {
                mailToPrint.sendMail("HeliGrapyh Druckanfrage", textPrint, bilder);
            }
            String textCustomer ="";
            if (druckerreiverstaendigen){
                paymentRequest.setSuccessUrl("http://localhost:8080/abgeschlossenOhneDownload");
                textCustomer=  "Sehr geehrte(r) Frau/Herr " + kunde.getName() + ", \r\n \r\n " +
                        "wir haben Ihre Bestellung erhalten und geben die Bilder an unsere Druckerei weiter." +
                        "Sollten Sie die Zahlungsweiße Vorrauskassa gewählt haben, so bitten wir Sie den Betrag aus der Rechnung umgehend zu überweisen. Verwenden Sie als Zahlungsreferenz bitte die Rechnungsnummer." +
                        "\r\n Bitte beachten Sie auch, dass sobald wir die Bestellung an die Druckerei weitergeleitet haben keine Stornierung mehr möglich ist. \n " +
                        "Ihr Heligraphy Team \n \n" +
                        "Unsere AGBs finden sie unter http://www.heligraphy.at/agb";

            }else {
                textCustomer=  "Sehr geehrte(r) Frau/Herr " + kunde.getName() + ", \r\n \r\n " +
                        "wir haben Ihre Bestellung erhalten. Wenn Sie die Zahlung geleistet haben, erhalten Sie den Bilddownload." +
                        "Sollten Sie die Zahlungsweiße Vorrauskassa gewählt haben, so bitten wir Sie den Betrag aus der Rechnung umgehend zu überweisen. Verwenden Sie als Zahlungsreferenz bitte die Rechnungsnummer." +
                        "\r\n Anschließend erhalten Sie den Download link.  \n Ihr Heligraphy Team \n\n" +
                        "Unsere AGBs finden sie unter http://www.heligraphy.at/agb" ;

            }



            Mail mailtoCustomer = new Mail();
            byte[] pdf = generateReport(bestellung);
            mailtoCustomer.sendMailWithBill(kunde.getEmail(), textCustomer, pdf);
            AbrechnungGenerieren();
        } catch (Exception e) {
            logger.error("----------------------------\n"+e.getMessage());
        }

        if (bestellung.getVersandart().equals("Onlineueberweisung")){
            Payment response = null;
            try {
                response = mpay24.paymentPage(paymentRequest);
            } catch (PaymentException e) {
                e.printStackTrace();
            }


            return "redirect:"+response.getRedirectLocation();
        }else {
            return "redirect:/abgeschlossenOhneDownload";
        }
    }



    protected void setPaymentRequest(BigDecimal amount,String transactionsid,String bilderliste, String email) {
        paymentRequest.setAmount(amount);
        paymentRequest.setTransactionID(transactionsid);
        paymentRequest.setSuccessUrl("http://localhost:8080/abgeschlossen/" +email+"/"+bilderliste);
    }

    public void AbrechnungGenerieren() {
        for (FotografAbrechnung ab : abrechnungen) {
            Preis p = ab.getPreis();
            double mwst;
            double druckpreis = 0.0;
            if (p.getPreis() == PreisPlan.BASIC.getValue()) {
                druckpreis = 6.0;
            }
            if (p.getPreis() == PreisPlan.PREMIUM.getValue()) {
                druckpreis = 80.0;
            }
            mwst = p.getPreis() / 1.2 * 0.2;
            ab.setGekauftwann();
            ab.setSumme((p.getPreis() - mwst - druckpreis) * ab.getFotograf().getAnteil() / 100);
            abrechnungDao.save(ab);
        }
    }


    public byte[] generateReport(Bestellung bs) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        //jasperReport = JasperCompileManager.compileReport("../resources/static/jasper/Invoice.jrxml");
        InputStream in = getClass().getResourceAsStream("/static/jasper/Invoice.jrxml");
        jasperReport = JasperCompileManager.compileReport(in);

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(bs.getBilder());
        parameter.put("ItemDataSource", itemsJRBean);

        parameter.put("kundennummer", String .valueOf(bs.getuser().getId()));
        parameter.put("summenetto", round2(bs.getSummenetto()));
        parameter.put("summebrutto", round2(bs.getSummebrutto()));
        parameter.put("summemwst", round2(bs.getSummemwst()));
        parameter.put("auftragsDatum", bs.getAuftragsDatum());
        parameter.put("idBestellung", bs.getIdBestellung());
        parameter.put("RAName", kunde.getName());
        parameter.put("RAStrasse", bs.getRechnungsAdresse().getAnschrift());
        parameter.put("RAOrt", bs.getRechnungsAdresse().getPlz() + " " + bs.getRechnungsAdresse().getOrt());
        parameter.put("RALand", bs.getRechnungsAdresse().getLand());
        parameter.put("Versandkosten",round2(bs.getVersankosten()));
        parameter.put("VAName", kunde.getName());
        parameter.put("VAStrasse", bs.getLieferAdresse().getAnschrift());
        parameter.put("VAOrt", bs.getLieferAdresse().getPlz() + " " + bs.getLieferAdresse().getOrt());
        parameter.put("VALand", bs.getLieferAdresse().getLand());


        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example4.pdf");


    }

    public Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void sendBestellung() {

    }

}
