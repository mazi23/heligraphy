package netgloo.controllers;

import netgloo.models.Bild;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.DisplayObjects.ShoppingChart;
import netgloo.models.daos.BildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mazi on 18.04.17.
 */
@Controller
public class ShopingCartController {

    @Autowired
    BildDao bildDao;
    @Autowired
    ShoppingChart shoppingChart;

    @RequestMapping(value = "/shoppingchartSum")
    public String start(Model model){

        model.addAttribute("Items",shoppingChart.getItems());

        return "shoppingChartSum";
    }


    //Holt alle Thumbnails für die Bilder aus dem Shopingchart  /picture-shoppingchartSum/2
     @RequestMapping(value="/shoppingchartSum/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return bildDao.findBildByid(Long.parseLong(id)).getThumbnail();
    }
}
