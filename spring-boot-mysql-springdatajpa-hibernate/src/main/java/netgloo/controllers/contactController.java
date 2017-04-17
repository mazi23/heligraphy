package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class contactController {
    @RequestMapping({"/contact-2"})
    public String start(Model model){
        return "contact-2";
    }
}
