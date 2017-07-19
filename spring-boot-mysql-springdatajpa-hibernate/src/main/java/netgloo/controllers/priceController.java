package netgloo.controllers;

import netgloo.models.daos.PreisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class priceController {

    @Autowired
    PreisDao preisDao;

    @RequestMapping({"/pricing-tables"})
    public String start(Model model){

        model.addAttribute("preise",preisDao.findAll());

        return "pricing-tables";
    }
}
