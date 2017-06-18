package netgloo.controllers;

import netgloo.Mail;
import netgloo.comands.ForgotPasswordCommand;
import netgloo.models.User;
import netgloo.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;

/**
 * Created by mazi on 20.05.17.
 */
@Controller
public class forgotPasswortController {

    @Autowired
    UserDao  userDao;


    @RequestMapping(value = "forgot-password.html")
    public String start(Model model){

        model.addAttribute("email",new ForgotPasswordCommand());

        return "forgot-password";
    }
    @RequestMapping(value = "/forgot-password/zuruecksetzen", method = RequestMethod.POST)
    public String zuruecksetzen(@ModelAttribute ForgotPasswordCommand email, BindingResult bindingResult) throws MessagingException {
        User u = userDao.findByEmail(email.getEmail());

       // System.out.print(email.getEmail());
        Mail mail = new Mail();
        String text = "Ihr Passwort lautet: "+u.getPasswort()+ "\n\r Ihr Heligraphy Team";
        mail.sendCustomMail(email.getEmail(),"Passwort vergessen",text );

        return "redirect:/forgot-password";
    }

}
