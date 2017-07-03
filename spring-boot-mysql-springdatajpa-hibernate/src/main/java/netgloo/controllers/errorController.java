package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 16.06.17.
 */
@Controller
public class errorController {

        @RequestMapping("/404.html")
        public String render404(Model model) {
            // Add model attributes
            return "404";
        }

    @RequestMapping("/backhome")
    public String backtoIndex(Model model) {
        // Add model attributes
        return "redirect:/index";
    }



}
