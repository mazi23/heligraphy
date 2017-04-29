package netgloo.controllers;

import netgloo.comands.AdressenCommand;
import netgloo.models.DisplayObjects.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 26.04.17.
 */
@Controller
public class CheckoutController {

    @Autowired
    ShoppingCart shoppingChart;

    @RequestMapping("/checkout")
    public String start(Model model){


        model.addAttribute("AdressenCommand",new AdressenCommand());
        return "checkout";
    }

    @RequestMapping("/VersandDetails")
    public String VersandDetails(@ModelAttribute(value = "AdressenCommand") AdressenCommand adressenCommand){


        return "overview";
    }
}
