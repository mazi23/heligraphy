package netgloo.controllers;

import netgloo.comands.SignInCommand;
import netgloo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String doregister(@Valid @ModelAttribute("SignInCommand") SignInCommand user, BindingResult bindingResult, Model model)
    {

        if(bindingResult.hasErrors()){
            return "signup";
        }

        return "index";
    }
}
