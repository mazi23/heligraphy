package netgloo.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.makernotes.SonyType1MakernoteDirectory;
import com.drew.metadata.jpeg.JpegCommentDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import netgloo.models.Bild;
import netgloo.models.DisplayObjects.BildDetailObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by mazi on 17.04.17.
 */

@Controller
public class pictrueDetailController {
    BildDetailObject bildDetailObject = new BildDetailObject();
    Bild b;
    @RequestMapping("/picture-details")
    public String start(@ModelAttribute(value = "bild") final Bild bild, Model model) throws ImageProcessingException, IOException {

        ByteArrayInputStream stream = new ByteArrayInputStream(bild.getDatei());
        Metadata metadata = ImageMetadataReader.readMetadata(stream);
        b=bild;


        ExifSubIFDDirectory directory
                = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        SonyType1MakernoteDirectory directory1 = metadata.getFirstDirectoryOfType(SonyType1MakernoteDirectory.class);

        try {
            bildDetailObject.setPreis(Integer.toString(bild.getPreis().getPreis()));
            bildDetailObject.setAufnahmeDatum( directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
            bildDetailObject.setId((int) bild.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        //bildDetailObject.setBild(bild.getThumbnail());
        //directory.getStringValue(ExifSubIFDDirectory.TAG_LENS_SPECIFICATION);
        //bildDetailObject.setFullImageSize(directory1.getStringValue(directory1.TAG_FULL_IMAGE_SIZE));
       // bildDetailObject.setWidth(directory1.getStringValue(JpegDirectory.TAG_IMAGE_WIDTH));

        model.addAttribute("bild",bildDetailObject);
        return "picture-details";
    }

    @RequestMapping(value="/picture-details/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImages(@PathVariable final String id) {

        return b.getDatei();
    }

}
