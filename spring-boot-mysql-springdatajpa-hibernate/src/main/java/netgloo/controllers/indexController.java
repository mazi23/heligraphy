package netgloo.controllers;

import net.sf.jasperreports.engine.*;
import netgloo.models.Code;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.daos.BildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class indexController {

    @Autowired
    BildDao bildDao;

    @Autowired
    ShoppingCart shoppingChart;

    @RequestMapping({"/", "/index",""})
    public String getIndex(Model model) throws JRException {

        //model.addAttribute("products", null);
        model.addAttribute("suchcode", new Code());
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("com/bio/ofm/mnu/views/reports/jasperReports/repAuditReport.jrxml");


        return "index";
    }



    @RequestMapping(value = "/suchen", method = RequestMethod.POST)
    public String sendCodeToGridView(@RequestParam(value = "code")String code, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("code",code);
        return "redirect:picture-grid";
    }


}
