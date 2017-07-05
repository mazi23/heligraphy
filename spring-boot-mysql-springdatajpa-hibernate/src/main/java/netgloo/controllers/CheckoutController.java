package netgloo.controllers;

import netgloo.comands.AdressenCommand;
import netgloo.comands.LoginCommand;
import netgloo.models.Adresse;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.PreisPlan;
import netgloo.models.User;
import netgloo.models.daos.AdresseDao;
import netgloo.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by mazi on 26.04.17.
 */
@Controller
public class CheckoutController {

    @Autowired
    ShoppingCart shoppingChart;
    @Autowired
    AdressenCommand adcommand;
    @Autowired
    UserDao userDao;
    @Autowired
    AdresseDao adresseDao;

    @RequestMapping("/checkout")
    public String start(Model model){
        model.addAttribute("AdressenCommand",new AdressenCommand());
        model.addAttribute("LoginCommand", new LoginCommand());
        boolean versandartNachnahme = true;
        for (ShoppingCartItem item :shoppingChart.getItems()){
            if (item.getPrice() == PreisPlan.BASIC.getValue() || item.getPrice() == PreisPlan.PREMIUM.getValue()){
                versandartNachnahme =false;
            }

        }
        model.addAttribute("nachnahme",versandartNachnahme);
        return "checkout";
    }

    @RequestMapping("/VersandDetails")
    public String VersandDetails(@Valid @ModelAttribute(value = "AdressenCommand") AdressenCommand adressenCommand,BindingResult bindingResult1,Model model, RedirectAttributes redirectAttributes){
        if(bindingResult1.hasErrors()){
            model.addAttribute("LoginCommand", new LoginCommand());

            return "checkout";
        }

        adcommand = adressenCommand;
        redirectAttributes.addFlashAttribute("adressenCommand",adressenCommand);
        return "redirect:/overview";
    }
    @RequestMapping("/VersandDetailsWithUser")
    public String VersandDetailsWithUser(@ModelAttribute(value = "LoginCommand") LoginCommand loginCommand,
                                         BindingResult bindingResult,Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("AdressenCommand",new AdressenCommand());
            return "checkout";
        }

        User user = userDao.findByUsername(loginCommand.getUsername());
        if (user!=null&&user.getPasswort().equals(loginCommand.getPassword())) {
            Adresse adresse = adresseDao.findByid(user.getAdresse().getId());
            AdressenCommand adressenCommand = new AdressenCommand();
            adressenCommand.setEmailRA(user.getEmail());
            adressenCommand.setLandRA(adresse.getLand());
            adressenCommand.setNameRA(user.getName());
            adressenCommand.setOrtRA(adresse.getOrt());
            adressenCommand.setPlzRA(String.valueOf(adresse.getPlz()));
            adressenCommand.setStrasseRA(adresse.getAnschrift());
            adressenCommand.setEmailVA(user.getEmail());
            adressenCommand.setLandVA(adresse.getLand());
            adressenCommand.setNameVA(user.getName());
            adressenCommand.setOrtVA(adresse.getOrt());
            adressenCommand.setPlzVA(String.valueOf(adresse.getPlz()));
            adressenCommand.setStrasseVA(adresse.getAnschrift());
            adressenCommand.setZahlungsart(loginCommand.getZahlungsart());
            redirectAttributes.addFlashAttribute("adressenCommand",adressenCommand);
            adcommand = adressenCommand;
            return "redirect:/overview";
        }else{
            model.addAttribute("AdressenCommand",new AdressenCommand());
            return "checkout";
        }


    }
}
