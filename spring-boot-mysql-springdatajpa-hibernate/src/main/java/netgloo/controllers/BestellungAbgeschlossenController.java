package netgloo.controllers;

import netgloo.models.DisplayObjects.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 30.04.17.
 */
public class BestellungAbgeschlossenController {

    @Autowired
    ShoppingCart shoppingCart;

    @RequestMapping(value = "/abgeschlossen")
    public String start(Model model){
        shoppingCart = new ShoppingCart();
        return "bestellungAbgeschlossen";
    }
}
