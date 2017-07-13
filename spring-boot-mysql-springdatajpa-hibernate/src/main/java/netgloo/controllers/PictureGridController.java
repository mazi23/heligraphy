package netgloo.controllers;

import netgloo.models.Bild;
import netgloo.models.Bildgruppe;
import netgloo.models.DisplayObjects.PictureGridObject;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.Websitecounter;
import netgloo.models.daos.BildDao;
import netgloo.models.daos.WebsiteCounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mazi on 17.04.17.
 *
 * Zuständig für die Darsellung nach der Eingabe des eindeutigen Codes
 */
@Controller
public class PictureGridController {

    @Autowired
    BildDao bildDao;

    HashMap<Integer, PictureGridObject> bilder ;

    @Autowired
    ShoppingCart shoppingChart;
    @Autowired
    WebsiteCounterDao websiteCounterDao;

    @RequestMapping(value = {"/picture-grid","/picture-grid.html"})
    public String getPictureSite(@ModelAttribute("code") String code, Model model, HttpServletRequest request, HttpServletResponse response){

        Websitecounter websitecounter = new Websitecounter();
        websitecounter.setSeite("Grid");
        websitecounter.setIp(request.getRemoteAddr());


        websiteCounterDao.save(websitecounter);


        bilder = new HashMap<Integer, PictureGridObject>();
        Bildgruppe bildgruppenId = bildDao.findByid(code);
        List<Object[]> list = bildDao.findThumbnailbyBildgruppe(bildgruppenId);
        for (Object[] i:list) {
            if(i!=null){
                PictureGridObject b = new PictureGridObject((Long) i[0],(byte[])i[1]);
                bilder.put(Math.toIntExact(b.getBildid()),b);
            }
        }


        response.setContentType(String.valueOf(MediaType.IMAGE_JPEG));
        model.addAttribute("Bilder",bilder);

        return "picture-grid";
    }

    @RequestMapping(value="/picture-grid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {
        //Bild b = bilder.get(Integer.parseInt(id));
        PictureGridObject b = bilder.get(Integer.parseInt(id));
        return b.getThubnail();
    }

    @RequestMapping(value = "/picture-grid/detail/{id}", method = RequestMethod.POST)
    public String toBildDetails(@PathVariable final String id, RedirectAttributes redirectAttributes){
        //Bild b = bilder.get(Integer.parseInt(id));
        Bild b = bildDao.findBildByid(Long.parseLong(id));
        redirectAttributes.addFlashAttribute("bild",b);

        return "redirect:/picture-details";
    }
}
//156134276
