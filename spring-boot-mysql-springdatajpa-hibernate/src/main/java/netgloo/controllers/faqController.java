package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 05.05.17.
 */
@Controller
public class faqController {

    @RequestMapping(value = {"/faq.html","faq"})
    public String start(){
        return "faq";
    }
}
