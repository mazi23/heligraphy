package netgloo.controllers;

import netgloo.models.Bild;
import netgloo.models.DisplayObjects.ShoppingCart;
import netgloo.models.daos.BildDao;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by mazi on 30.04.17.
 */
@Controller
public class BestellungAbgeschlossenController {

    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    BildDao bildDao;

    @RequestMapping(value = "/abgeschlossen")
    public String start(Model model){
        //shoppingCart = new ShoppingCart();
        return "bestellungAbgeschlossen";
    }

    @RequestMapping(value = "/abgeschlossen/{bildcodes}")
    @ResponseBody
    public byte[]  download(@PathVariable List<String> bildcodes, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);


        //packing files
        for (int i = 0;i<bildcodes.size();i++){
            Bild a = bildDao.findBildByCode(bildcodes.get(i));
            if (a!=null) {
                //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
                zipOutputStream.putNextEntry(new ZipEntry("Bild" + i + ".jpg"));
                ByteArrayInputStream fileInputStream = new ByteArrayInputStream(a.getDatei());

                IOUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }

        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        //RequestDispatcher rd=request.getRequestDispatcher("/abgeschlossen/fertig");
        //rd.forward(request, response);//method may be include or forward
        return byteArrayOutputStream.toByteArray();
    }
    @RequestMapping(value = "/abgeschlossen/fertig",method = RequestMethod.GET)
    public String  showAbgeschlossen() {


        return "bestellungAbgeschlossen";
    }

}
