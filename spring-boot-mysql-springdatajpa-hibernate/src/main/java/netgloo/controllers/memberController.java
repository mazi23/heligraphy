package netgloo.controllers;

import netgloo.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mazi on 18.05.17.
 */
@Controller
public class memberController {

    User loggedinUser = new User();

    @RequestMapping(value = "/memberArea")
    public String start(Model model, @ModelAttribute(value = "loggedInUser") User user){

        if (user.getUsername()!=null&&user.getPasswort()!=null){
            model.addAttribute("currentUser",user);
            return "memberArea";
        }
        else return "index";
    }

    @RequestMapping(value = "/memberArea/aendern",method = RequestMethod.POST)
    public String aendern(Model model, @ModelAttribute(value = "currentUser") User user){
        System.out.printf("----");
        return "index";
    }
}
