package com.team3.SWTestingTeam3;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.cloud.firestore.Firestore;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team3.SWTestingTeam3.Functions;
import com.team3.SWTestingTeam3.CreditCardPayoff;
import com.team3.SWTestingTeam3.FireStore;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin
@RestController
public class Controller {

  Functions functions;
  FireStore fire;

  @GetMapping("/")
  public String index() {
    return "[THIS IS FOR THE DEMO] ---- Available endpoints: 1. /credit-card-payoff 2. /simple-savings-calculator 3. /credit-card-min-payment-calculator 4. /cd-calculator 5. /firestoneAuth";
  }

  @PostMapping("/firestoreAuth")
  public String firestoreAuth(@RequestBody AddUser fs) throws Exception {
    FireStore quickStart = new FireStore();
    String apikey = quickStart.addUser(fs.orgName, fs.industry, fs.fullName, fs.email);
    return apikey;
  }

  @PostMapping("/revokeKey")
  public String revokeKey(@RequestParam String apiKey) throws Exception {
    FireStore quickStart = new FireStore();
    return quickStart.revokeKey(apiKey);
  }

  @PostMapping("/credit-card-payoff")
  public HashMap<String, Double> calculatePayoff(
      @RequestParam String apiKey,
      @RequestBody CreditCardPayoff creditCardPayoff,
      HttpServletRequest request)
      throws Exception {
    FireStore fsObject = new FireStore();
    Boolean validAPIKey = fsObject.validateAPIKey(apiKey);

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

    String IPAddress = request.getRemoteAddr();

    ArrayList<Double> result =
        Functions.option1(
            creditCardPayoff.creditCardBalance,
            creditCardPayoff.creditCardInterestRate,
            creditCardPayoff.numMonthsToPayOff);

    HashMap<String, Double> res = new HashMap<>();
    res.put("monthly-payment", result.get(3));
    res.put("total-principle-paid", result.get(4));
    res.put("total-interest-paid", result.get(5));

    if (validAPIKey) {
      fsObject.addCreditCardPayoffAPICall(IPAddress, timeStamp, creditCardPayoff, res);
    } else {
      HashMap<String, Double> error = new HashMap<>();

      error.put("Invalid API key!", 401.0);

      return error;
    }

    return res;
  }

  @PostMapping("/simple-savings-calculator")
  public HashMap<String, Float> calculateSavings(
      @RequestParam String apiKey,
      @RequestBody SimpleSavingsCalculator simpleSavingsCalculator,
      HttpServletRequest request)
      throws Exception {
    FireStore fsObject = new FireStore();
    Boolean validAPIKey = fsObject.validateAPIKey(apiKey);

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

    String IPAddress = request.getRemoteAddr();

    ArrayList<Float> result =
        Functions.option2(
            simpleSavingsCalculator.intialDeposit,
            simpleSavingsCalculator.monthlyContribution,
            simpleSavingsCalculator.years,
            simpleSavingsCalculator.simpleInterest);

    HashMap<String, Float> res = new HashMap<>();
    res.put("savings-total", result.get(4));
    res.put("total-contributions", result.get(5));
    res.put("interest-earned", result.get(6));

    if (validAPIKey) {
      fsObject.addCalculateSavingsAPICall(IPAddress, timeStamp, simpleSavingsCalculator, res);
    } else {
      HashMap<String, Float> error = new HashMap<>();

      error.put("Invalid API key!", 401f);

      return error;
    }

    return res;
  }

  @PostMapping("/credit-card-min-payment-calculator")
  public HashMap<String, Float> calculateMinPayment(
      @RequestParam String apiKey,
      @RequestBody MinimumPaymentCalculator minimumPaymentCalculator,
      HttpServletRequest request)
      throws Exception {
    FireStore fsObject = new FireStore();
    Boolean validAPIKey = fsObject.validateAPIKey(apiKey);

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

    String IPAddress = request.getRemoteAddr();

    ArrayList<Float> result =
        Functions.option3(
            minimumPaymentCalculator.creditCardBalance,
            minimumPaymentCalculator.creditCardInterestRate,
            minimumPaymentCalculator.minPaymentPercentage);

    HashMap<String, Float> res = new HashMap<>();
    res.put("monthly-payment", result.get(3));
    res.put("number-months", result.get(4));
    res.put("amount-paid", result.get(5));

    if (validAPIKey) {
      fsObject.addCalculateMinPaymentAPICall(IPAddress, timeStamp, minimumPaymentCalculator, res);
    } else {
      HashMap<String, Float> error = new HashMap<>();

      error.put("Invalid API key!", (float) 401);

      return error;
    }

    return res;
  }

  @PostMapping("/cd-calculator")
  public HashMap<String, Float> cdCalculator(
      @RequestParam String apiKey,
      @RequestBody CDCalculator cdCalculator,
      HttpServletRequest request)
      throws Exception {
    FireStore fsObject = new FireStore();
    Boolean validAPIKey = fsObject.validateAPIKey(apiKey);

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

    String IPAddress = request.getRemoteAddr();

    ArrayList<Float> result =
        Functions.option4(cdCalculator.deposit, cdCalculator.years, cdCalculator.apy);

    HashMap<String, Float> res = new HashMap<>();
    res.put("total-balance", result.get(3));
    res.put("total-interest-earned", result.get(4));

    if (validAPIKey) {
      fsObject.addCDCalculatorAPICall(IPAddress, timeStamp, cdCalculator, res);
    } else {
      HashMap<String, Float> error = new HashMap<>();

      error.put("Invalid API key!", (float) 401);

      return error;
    }

    return res;
  }
}
