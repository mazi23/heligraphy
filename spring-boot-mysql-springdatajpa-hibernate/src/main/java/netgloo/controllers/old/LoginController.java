package netgloo.controllers.old;


import netgloo.comands.LoginCommand;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by mazi on 12.01.17.
 */
@Controller
public class LoginController {



    @RequestMapping("/login")
    public String showLoginForm(Model model){

        model.addAttribute("loginCommand", new LoginCommand());



        return "login";
    }

    @RequestMapping("logout-success")
    public String yourLoggedOut(){

        return "logout-success";
    }

     //@RequestMapping(value = "/dologin", method = RequestMethod.GET)
    public String doLogin(@Valid LoginCommand loginCommand, BindingResult bindingResult){

         UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginCommand.getUsername(), loginCommand.getUsername());

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String currentPrincipalName = authentication.getName();


         if(bindingResult.hasErrors()){
            return "loginform";
        }

        return "index";
    }

}
