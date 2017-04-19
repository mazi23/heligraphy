package netgloo.controllers;

import netgloo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 01.02.17.
 */
@Controller
public class RegisterController {


    @RequestMapping(value = {"/signup.html","/signup"})
    public String start(Model model){

        model.addAttribute("user",new User());

        return "signup";
    }


    @RequestMapping(value = "/register")
    public String register(@ModelAttribute("user") User user,Model model)
    {
        return "";
    }
}
