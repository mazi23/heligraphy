package netgloo.controllers;

import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.daos.BildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by mazi on 18.04.17.
 */
@Controller
public class ShopingCartController {

    @Autowired
    BildDao bildDao;
    @Autowired
    ShoppingCart shoppingCart;

    @RequestMapping(value = "/shoppingchartSum")
    public String start(Model model){
        List<ShoppingCartItem> l = shoppingCart.getItems();
        model.addAttribute("Items",shoppingCart.getItems());

        return "shoppingChartSum";
    }


    //Holt alle Thumbnails für die Bilder aus dem Shopingchart  /picture-shoppingchartSum/2
     @RequestMapping(value="/shoppingchartSum/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return bildDao.findBildByid(Long.parseLong(id)).getThumbnail();
    }

    @RequestMapping(value = "/weiterEinkaufen")
    public String weiterEinkaufen(Model model,RedirectAttributes redirectAttributes){
        System.out.println(shoppingCart.getBildgruppe());
        redirectAttributes.addFlashAttribute("code",shoppingCart.getBildgruppe());
        return "redirect:picture-grid";
    }

    @RequestMapping(value="/itemDelete/{id}", method = RequestMethod.GET)
    public String loeschen(@PathVariable final String id) {
        shoppingCart.getItems().remove(Integer.parseInt(id));

        return "redirect:/shoppingchartSum";
    }
}
