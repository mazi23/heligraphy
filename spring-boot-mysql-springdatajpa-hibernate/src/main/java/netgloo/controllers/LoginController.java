package netgloo.controllers;

import netgloo.models.DisplayObjects.ShoppingChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 19.04.17.
 */
@Controller
public class LoginController {

    @Autowired
    ShoppingChart shoppingChart;

    @RequestMapping(value = {"/login.html","/login"})
    public String start(Model model){

        return "login";
    }
}
