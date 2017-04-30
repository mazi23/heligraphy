package netgloo.controllers;

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

import java.sql.Timestamp;

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
        overviewPrice.setGesamtkosten(overviewPrice.getZwischenSumme()+overviewPrice.getVersandkosten());




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
/*
    @RequestMapping(value = "/BestellungAbsenden")
    public String bestellungAbsenden(Model model)
    {


    return "";
    }


    public void sendBestellung()
    {

    }
*/
}
