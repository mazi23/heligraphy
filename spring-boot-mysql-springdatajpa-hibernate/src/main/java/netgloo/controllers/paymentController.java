package netgloo.controllers;

import com.mpay24.payment.Mpay24;
import com.mpay24.payment.PaymentException;
import com.mpay24.payment.data.Payment;
import com.mpay24.payment.data.PaymentRequest;
import com.mpay24.payment.type.CreditCardPaymentType;
import com.mpay24.payment.type.PaymentTypeData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by mazi on 14.05.17.
 */
@Controller
public class paymentController extends PaymentRequest {
//94894
  //?x#?JQQ6WC
  //JoY?Mz8a9w
  Mpay24 mpay24 = new Mpay24("94894", "JoY?Mz8a9w", Mpay24.Environment.TEST);

  @RequestMapping(value = "/payment")
    public String start(Model model){
        return "payment";
    }

    @RequestMapping(value = "/pay")
    public String pay(Model model) throws PaymentException {
        Payment response = mpay24.paymentPage(getTestPaymentRequest());
     return "redirect:"+response.getRedirectLocation();



    }
    protected PaymentRequest getTestPaymentRequest() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal(0.01));
        paymentRequest.setTransactionID("2");
        paymentRequest.setSuccessUrl("http://www.heligraphy.at/upload");
        return paymentRequest;
    }
    protected PaymentTypeData getVisaTestData() throws ParseException {
        CreditCardPaymentType paymentType = new CreditCardPaymentType();
        paymentType.setPan("4444333322221111");
        paymentType.setCvc("123");
        paymentType.setExpiry(getCreditCardMonthYearDate("12/2016"));
        paymentType.setBrand(CreditCardPaymentType.Brand.VISA);
        return paymentType;
    }

    private Date getCreditCardMonthYearDate(String s) {
        return new Date();
    }


}
