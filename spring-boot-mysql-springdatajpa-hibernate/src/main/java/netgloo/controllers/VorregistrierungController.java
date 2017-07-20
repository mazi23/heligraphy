package netgloo.controllers;

import netgloo.comands.VorregistrierungCommand;
import netgloo.models.Adresse;
import netgloo.models.User;
import netgloo.models.Vorregistrierung;
import netgloo.models.Websitecounter;
import netgloo.models.daos.AdresseDao;
import netgloo.models.daos.UserDao;
import netgloo.models.daos.VorregistrierungDao;
import netgloo.models.daos.WebsiteCounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by mazi on 19.07.17.
 */
@Controller
public class VorregistrierungController {


    @Autowired
    AdresseDao adresseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    VorregistrierungDao vorregistrierungDao;

    @Autowired
    WebsiteCounterDao websiteCounterDao;

    @RequestMapping(value = {"/vorregistrierung","/vorregistrierung.html"})
    public String start(Model model, HttpServletRequest request){
        model.addAttribute("vorregist", new VorregistrierungCommand());

        try{
            Websitecounter websitecounter = new Websitecounter();
            websitecounter.setSeite("Vorregistrierung");
            websitecounter.setIp(request.getRemoteAddr());
            websitecounter.setDatum(new Date());
            websitecounter.setInfo("geöffnet");

            websiteCounterDao.save(websitecounter);
        }catch (Exception e){

        }
        return "vorregistrierung";
    }


    @RequestMapping(value = {"/vorregistrierungSpeichern"})
    public String vorregistrierungSpeichern(@Valid @ModelAttribute("vorregist") VorregistrierungCommand vorregistrierungCommand, BindingResult bindingResult, Model model, HttpServletRequest request){

        if(bindingResult.hasErrors()) {
            model.addAttribute("in","in");
            return "vorregistrierung";
        }

        Vorregistrierung vorregistrierung = new Vorregistrierung();

        Adresse adresse = new Adresse();
        adresse.setName(vorregistrierungCommand.getName());
        adresse.setAnschrift(vorregistrierungCommand.getStrasse());
        adresse.setOrt(vorregistrierungCommand.getOrt());
        adresse.setLand(vorregistrierungCommand.getLand());
        adresse.setPlz(Integer.parseInt(vorregistrierungCommand.getPlz()));

        adresseDao.save(adresse);


        User u = new User();
        u.setUsername(vorregistrierungCommand.getEmail());
        u.setName(vorregistrierungCommand.getName());
        u.setAdresse(adresse);
        u.setEmail(vorregistrierungCommand.getEmail());
        u.setTelefon(vorregistrierungCommand.getTel());
        u.setEnabled(true);
        u.setPasswort(vorregistrierungCommand.getName());

        userDao.save(u);

        vorregistrierung.setAdresse(adresse);
        vorregistrierung.setUser(u);
        vorregistrierung.setKoordinaten(vorregistrierungCommand.getKoordinaten());

        vorregistrierungDao.save(vorregistrierung);


        try{
            Websitecounter websitecounter = new Websitecounter();
            websitecounter.setSeite("Vorregistrierung");
            websitecounter.setIp(request.getRemoteAddr());
            websitecounter.setDatum(new Date());
            websitecounter.setInfo("abgesendet");

            websiteCounterDao.save(websitecounter);
        }catch (Exception e){

        }

        return "redirect:vorregistrierung";
    }
}
