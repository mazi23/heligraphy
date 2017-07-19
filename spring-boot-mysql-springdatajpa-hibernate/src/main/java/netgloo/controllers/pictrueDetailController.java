package netgloo.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.makernotes.SonyType1MakernoteDirectory;
import netgloo.Application;
import netgloo.models.Bild;
import netgloo.models.DisplayObjects.BildDetailObject;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.DisplayObjects.ShoppingCartItem;
import netgloo.models.Websitecounter;
import netgloo.models.daos.PreisDao;
import netgloo.models.daos.WebsiteCounterDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mazi on 17.04.17.
 */

@Controller
@Component
public class pictrueDetailController {

    @Autowired
    ShoppingCart shoppingChart;

    @Autowired
    PreisDao preisDao;

    @Autowired
    WebsiteCounterDao websiteCounterDao;


    @Autowired
    BildDetailObject bildDetailObject = new BildDetailObject();

    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    Bild b;
    @RequestMapping("/picture-details")
    public String start(@ModelAttribute(value = "bild") final Bild bild, Model model, HttpServletResponse response, HttpServletRequest request) throws ImageProcessingException, IOException {



/*
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }*/


        try {
            System.out.println(bildDetailObject.getId());
            if (bild!=null||bildDetailObject.getId()!=bild.getId()) {

                ByteArrayInputStream stream = new ByteArrayInputStream(bild.getDatei());
                Metadata metadata = ImageMetadataReader.readMetadata(stream);
                b = bild;
                ExifSubIFDDirectory directory
                        = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                SonyType1MakernoteDirectory directory1 = metadata.getFirstDirectoryOfType(SonyType1MakernoteDirectory.class);


                HashMap<String, String> data = new HashMap<>();
                //bildDetailObject.setPreis(Integer.toString(bild.getPreis().getPreis()));
                //DateFormat formatter = new SimpleDateFormat("MM.dd.yyyy", Locale.GERMAN);
                //String dt = formatter.format(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
                //data.put("Datum",dt);


                //data.put("Belichtungszeit",directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME) );

                //data.put("ISO",directory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));


                bildDetailObject.setAufnahmeDatum(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
                bildDetailObject.setMetadata(data);
            }else{
                System.out.println("lere");
            }
                Websitecounter websitecounter = new Websitecounter();
            websitecounter.setSeite("Detail");
            websitecounter.setIp(request.getRemoteAddr());
            websitecounter.setDatum(new Date());
            websitecounter.setInfo(bild.getId()+"");

            websiteCounterDao.save(websitecounter);


        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            bildDetailObject.setId((int) bild.getId());


            //bildDetailObject.setBild(bild.getThumbnail());
            //directory.getStringValue(ExifSubIFDDirectory.TAG_LENS_SPECIFICATION);
            //bildDetailObject.setFullImageSize(directory1.getStringValue(directory1.TAG_FULL_IMAGE_SIZE));
            // bildDetailObject.setWidth(directory1.getStringValue(JpegDirectory.TAG_IMAGE_WIDTH));

            model.addAttribute("bild",bildDetailObject);


            model.addAttribute("preise",preisDao.findAll());

            model.addAttribute("Item",new ShoppingCartItem());

            response.setContentType(String.valueOf(MediaType.IMAGE_JPEG));
            //response.setContentLength(b.getDatei().length);
        }
        return "picture-details";
    }

    @RequestMapping(value="/picture-details/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id, HttpServletResponse response) {
        response.setContentType(String.valueOf(MediaType.IMAGE_JPEG));
        //response.setContentLength(b.getDatei().length);
        return b.getDatei();
    }

    @RequestMapping(value = "/addToChart", method = RequestMethod.POST)
    public String addToChart(@ModelAttribute("Item") ShoppingCartItem shoppingCartItem)
    {

        int priceid = (int) shoppingCartItem.getPrice();
        shoppingCartItem.setPrice(preisDao.findOne(Long.valueOf(priceid)).getPreis());
        shoppingCartItem.setId(bildDetailObject.getId());
        shoppingCartItem.setQuantity(1);
        shoppingChart.setBildgruppe( b.getBildgruppe().getUniqCode());

        if (shoppingChart.getItems()==null){
            shoppingChart.items = new ArrayList<>();
        }
        shoppingChart.getItems().add(shoppingCartItem);
        return "redirect:/shoppingchartSum";
    }


}
