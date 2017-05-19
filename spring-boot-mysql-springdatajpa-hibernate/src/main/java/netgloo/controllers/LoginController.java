package netgloo.controllers;

import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.User;
import netgloo.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by mazi on 19.04.17.
 */
@Controller
public class LoginController {

    @Autowired
    ShoppingCart shoppingChart;
    @Autowired
    UserDao userDao;

    @RequestMapping(value = {"/login.html","/login"})
    public String start(Model model){
        model.addAttribute("User",new User());
        return "login";
    }

    @RequestMapping(value = {"/authorize"})
    public String login(@ModelAttribute User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "redirect:/login";
        }
        User u = userDao.findByUsername(user.getUsername());
        if(u!=null&&user.getPasswort().equals(u.getPasswort())&&user.getUsername().equals(u.getUsername())&&u.isEnabled()){
            redirectAttributes.addAttribute("loggedInUser",u);
            return "redirect:/memberArea";
        }else{
            return "redirect:/login";
        }

    }
}
