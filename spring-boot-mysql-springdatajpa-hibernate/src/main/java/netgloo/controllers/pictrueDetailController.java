package netgloo.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.makernotes.SonyType1MakernoteDirectory;
import netgloo.models.Bild;
import netgloo.models.DisplayObjects.BildDetailObject;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.DisplayObjects.ShoppingChart;
import netgloo.models.daos.PreisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by mazi on 17.04.17.
 */

@Controller
@Component
public class pictrueDetailController {

    @Autowired
    ShoppingChart shoppingChart;

    @Autowired
    PreisDao preisDao;

    BildDetailObject bildDetailObject = new BildDetailObject();
    Bild b;
    @RequestMapping("/picture-details")
    public String start(@ModelAttribute(value = "bild") final Bild bild, Model model) throws ImageProcessingException, IOException {

        ByteArrayInputStream stream = new ByteArrayInputStream(bild.getDatei());
        Metadata metadata = ImageMetadataReader.readMetadata(stream);
        b=bild;

        /*for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }*/

        ExifSubIFDDirectory directory
                = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        SonyType1MakernoteDirectory directory1 = metadata.getFirstDirectoryOfType(SonyType1MakernoteDirectory.class);


        HashMap<String,String> data = new HashMap<>();
        try {
            //bildDetailObject.setPreis(Integer.toString(bild.getPreis().getPreis()));
            DateFormat formatter = new SimpleDateFormat("MM.dd.yyyy", Locale.GERMAN);
            String dt = formatter.format(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
            data.put("Datum",dt);


            data.put("Belichtungszeit",directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME) );

            data.put("ISO",directory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));

            //data.put("Kamera",directory.getString(ExifSubIFDDirectory.TAG_MAKE) + " " + directory.getString(ExifSubIFDDirectory.TAG_MODEL));
            bildDetailObject.setAufnahmeDatum( directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        bildDetailObject.setId((int) bild.getId());
        bildDetailObject.setMetadata(data);

        //bildDetailObject.setBild(bild.getThumbnail());
        //directory.getStringValue(ExifSubIFDDirectory.TAG_LENS_SPECIFICATION);
        //bildDetailObject.setFullImageSize(directory1.getStringValue(directory1.TAG_FULL_IMAGE_SIZE));
       // bildDetailObject.setWidth(directory1.getStringValue(JpegDirectory.TAG_IMAGE_WIDTH));

        model.addAttribute("bild",bildDetailObject);


        model.addAttribute("preise",preisDao.findAll());

        model.addAttribute("Item",new ShoppingCartItem());

        return "picture-details";
    }

    @RequestMapping(value="/picture-details/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return b.getDatei();
    }

    @RequestMapping(value = "/addToChart", method = RequestMethod.POST)
    public String addToChart(@ModelAttribute("Item") ShoppingCartItem shoppingCartItem)
    {

        int priceid = shoppingCartItem.getPrice();
        shoppingCartItem.setPrice(preisDao.findOne(Long.valueOf(priceid)).getPreis());
        shoppingCartItem.setId(bildDetailObject.getId());
        shoppingCartItem.setQuantity(1);

        if (shoppingChart.getItems()==null){
            shoppingChart.items = new ArrayList<>();
        }
        shoppingChart.getItems().add(shoppingCartItem);
        return "redirect:/shoppingchartSum";
    }


}
