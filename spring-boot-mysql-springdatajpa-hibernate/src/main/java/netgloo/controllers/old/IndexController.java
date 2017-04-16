package netgloo.controllers.old;


import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import netgloo.models.Code;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.FrontendBild;
import netgloo.models.daos.BestellungDao;
import netgloo.models.daos.BildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by jt on 1/20/16.
 */
@Controller
public class IndexController {

    @Autowired
    BildDao bildDao;


    @RequestMapping({"/", "index"})
    public String getIndex(Model model){

        model.addAttribute("products", null);
        model.addAttribute("suchcode", new Code());



        return "index";
    }

    @RequestMapping(value = "/CodeSuchen", method = RequestMethod.POST)
    public String greetingSubmit(@RequestParam(value = "code")String code, RedirectAttributes redirectAttributes) {
        //HashMap<Integer, FrontendBild> bilder = new HashMap<Integer, FrontendBild>();
        HashMap<Integer, Bild> bilder = new HashMap<Integer, Bild>();




        Bildgruppe bildgruppenId = bildDao.findByid(code);
        System.out.println(bildgruppenId);
        /*List<Objects[]> list =  bildDao.findBilder(bildgruppenId);//HibernateUtil.getSession().createQuery("SELECT b.id,b.thumbnail,b.bildgruppeId from Bild b where b.bildgruppeId="+bildgruppenId).list();


        bilder =  new HashMap<Integer, FrontendBild>();

        for (Object[] o:list) {
            if(o[0]!=null){
                FrontendBild bild = new FrontendBild();
                bild.setBild((byte[]) o[1]);
                bild.setBildid(Math.toIntExact((Long) o[0]));
                bild.setId(Math.toIntExact((Long) o[0]));

                bilder.put(Math.toIntExact((Long) o[0]),bild);
            }
        }
*/

        List<Bild> list = bildDao.findBildermitgruppe(bildgruppenId);
        for (Bild b:list) {
            if(b!=null){

                bilder.put(Math.toIntExact(b.getId()),b);
            }
        }

        redirectAttributes.addFlashAttribute("bilderListe",bilder);

        return "redirect:bilder";
    }

    @RequestMapping("secured")
    public String secured(){
        return "secured";
    }

}
