package netgloo.controllers;

import netgloo.models.Code;
import netgloo.models.daos.BildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class indexController {

    @Autowired
    BildDao bildDao;

    @RequestMapping({"/", "/index",""})
    public String getIndex(Model model){

        //model.addAttribute("products", null);
        model.addAttribute("suchcode", new Code());



        return "index";
    }

/*
    @RequestMapping(value = "/CodeSuchen", method = RequestMethod.POST)
    public String greetingSubmit(@RequestParam(value = "code")String code, RedirectAttributes redirectAttributes) {

        HashMap<Integer, Bild> bilder = new HashMap<Integer, Bild>();
        Bildgruppe bildgruppenId = bildDao.findByid(code);
        System.out.println(bildgruppenId);


        List<Bild> list = bildDao.findBildermitgruppe(bildgruppenId);
        for (Bild b:list) {
            if(b!=null){

                bilder.put(Math.toIntExact(b.getId()),b);
            }
        }

        redirectAttributes.addFlashAttribute("bilderListe",bilder);

        return "redirect:bilder";
    }
*/
    @RequestMapping(value = "/suchen", method = RequestMethod.POST)
    public String sendCodeToGridView(@RequestParam(value = "code")String code, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("code",code);
        return "redirect:picture-grid";
    }


}
