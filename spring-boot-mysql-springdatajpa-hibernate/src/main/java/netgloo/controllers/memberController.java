package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 18.05.17.
 */
@Controller
public class memberController {

    @RequestMapping(value = "/memberArea")
    public String start(Model model){
        return "memberArea";
    }
}
