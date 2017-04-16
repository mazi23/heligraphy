package netgloo.controllers;

import netgloo.models.Code;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class indexController {

    @RequestMapping({"/", "/index",""})
    public String getIndex(Model model){

        //model.addAttribute("products", null);
        //model.addAttribute("suchcode", new Code());



        return "index";
    }

}
