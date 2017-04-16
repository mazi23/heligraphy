package netgloo.controllers.old;


import netgloo.models.Bild;
import netgloo.models.DisplayObjects.BackendBild;
import netgloo.models.DisplayObjects.FrontendBild;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by mazi on 15.01.17.
 */
@Controller
public class BilderController {


    HashMap<Integer, Bild> bilder = new HashMap<Integer, Bild>();

    @RequestMapping(value = "/bilder", method = RequestMethod.GET)
    public String controlMapping2(
            @ModelAttribute("bilderListe") final HashMap<Integer,Bild> b,
           // @ModelAttribute("suchcode") Code suchcode,
            final Model model) {

        bilder=b;

        model.addAttribute("Bilder",bilder);

        return "bilder";
    }
    @RequestMapping(value="/bilder/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {
        Bild b = bilder.get(Integer.parseInt(id));
        return b.getDatei();
    }

}
