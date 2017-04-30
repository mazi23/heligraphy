package netgloo.controllers;

import netgloo.comands.AdressenCommand;
import netgloo.models.DisplayObjects.ShoppingCart;
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

    @RequestMapping("/checkout")
    public String start(Model model){
        model.addAttribute("AdressenCommand",new AdressenCommand());
        return "checkout";
    }

    @RequestMapping("/VersandDetails")
    public String VersandDetails(@Valid @ModelAttribute(value = "AdressenCommand") AdressenCommand adressenCommand, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/checkout";
        }
        adcommand = adressenCommand;
        redirectAttributes.addFlashAttribute("adressenCommand",adressenCommand);
        return "redirect:/overview";
    }
}
