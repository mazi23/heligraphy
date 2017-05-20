package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserDao userDao;

    User loggedinUser = new User();

    @RequestMapping(value = "/memberArea")
    public String start(Model model, @ModelAttribute(value = "loggedInUser") User user){

        if (user.getUsername()!=null&&user.getPasswort()!=null){
            model.addAttribute("currentUser",user);
            return "memberArea";
        }
        else return "redirect:/login";
    }

    @RequestMapping(value = "/memberArea/aendern",method = RequestMethod.POST)
    public String aendern(Model model, @ModelAttribute(value = "currentUser") User user){
        userDao.save(user);
        return "index";
    }
    @RequestMapping(value = "/memberArea/loeschen")
    public String loeschen(Model model, @ModelAttribute(value = "currentUser") User user){

        userDao.delete(user);
        return "index";
    }
}
