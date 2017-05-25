package netgloo.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import netgloo.models.Bestellung;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mazi on 25.05.17.
 */
@Controller
public class billController {

    @RequestMapping("/rechnungerstellen")
    public String start(Model model){
        return "PDFAbrechnung";
    }


    public void generateReport(Bestellung bs) throws JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        //jasperReport = JasperCompileManager.compileReport("../resources/static/jasper/Invoice.jrxml");
        InputStream in = getClass().getResourceAsStream("/static/jasper/Invoice.jrxml");
        jasperReport = JasperCompileManager.compileReport(in);

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(bs.getBilder());
        parameter.put("ItemDataSource", itemsJRBean);
/*
        parameter.put("summenetto", round2(bs.getSummenetto()));
        parameter.put("summebrutto", round2(bs.getSummebrutto()));
        parameter.put("summemwst", round2(bs.getSummemwst()));
        parameter.put("auftragsDatum", bs.getAuftragsDatum());
        parameter.put("idBestellung", bs.getIdBestellung());
        parameter.put("RAName", kunde.getName());
        parameter.put("RAStrasse", bs.getRechnungsAdresse().getAnschrift());
        parameter.put("RAOrt", bs.getRechnungsAdresse().getPlz() + " " + bs.getRechnungsAdresse().getOrt());
        parameter.put("RALand", bs.getRechnungsAdresse().getLand());
        parameter.put("Versandkosten",round2(bs.getVersankosten()));
        parameter.put("VAName", kunde.getName());
        parameter.put("VAStrasse", bs.getLieferAdresse().getAnschrift());
        parameter.put("VAOrt", bs.getLieferAdresse().getPlz() + " " + bs.getLieferAdresse().getOrt());
        parameter.put("VALand", bs.getLieferAdresse().getLand());


        jasperPrint =
                JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperExportManager.exportReportToPdfFile(jasperPrint,"./Example4.pdf");

*/
    }
}
