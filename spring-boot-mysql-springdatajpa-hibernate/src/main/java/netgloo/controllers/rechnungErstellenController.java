package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 20.05.17.
 */
@Controller
public class rechnungErstellenController {

    @RequestMapping(value = {"rechnungerstellen.html","rechnungerstellen"})
    public String start(Model model){
        return "rechnungerstellen";
    }

}
