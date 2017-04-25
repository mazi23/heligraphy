package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 21.04.17.
 */
@Controller
public class AgbController {

    @RequestMapping(value = {"/agb.html","/agb"})
    public String start(){
        return "agb";
    }
}
