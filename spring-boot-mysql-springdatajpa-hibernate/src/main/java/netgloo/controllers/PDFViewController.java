package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by mazi on 07.05.17.
 */
@Controller
public class PDFViewController {




    @RequestMapping(value = "/PDFAbrechnung", method = RequestMethod.GET)
    protected String preivewSection(
            HttpServletRequest request,
            HttpSession httpSession,
            HttpServletResponse response, @ModelAttribute (value = "report") byte[] report) {
        try {

            response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
            response.setDateHeader("Expires", -1);
            response.setContentType("application/pdf");
            response.setContentLength(report.length);
            response.getOutputStream().write(report);
        } catch (Exception ioe) {
        } finally {
        }
        return null;
    }
}
