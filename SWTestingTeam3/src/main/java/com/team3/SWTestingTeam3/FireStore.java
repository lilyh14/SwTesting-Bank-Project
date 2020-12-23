package com.team3.SWTestingTeam3;
// package com.example.firestore;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

public class FireStore {
  private Firestore db;

  private static String lastTS;

  private static String lastAPIKey;

  public FireStore() {
    Firestore db = FirestoreOptions.getDefaultInstance().getService();
    this.db = db;
  }

  public String getLastTS() {
    return lastTS;
  }

  public String getLastAPIKey() { return lastAPIKey; }

  // build instance of db
  public FireStore(String projectId) throws Exception {
    FirestoreOptions firestoreOptions =
        FirestoreOptions.getDefaultInstance().toBuilder()
            .setProjectId(projectId)
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build();
    Firestore db = firestoreOptions.getService();

    this.db = db;
  }

  Firestore getDb() {
    return db;
  }

  String addUser(String orgName, String industry, String fullName, String email) throws Exception {
    System.out.println("########## Adding User ##########");
    DocumentReference docRef = db.collection("users").document(fullName);
    String API_KEY_HASH = RandomStringUtils.random(20, true, true);
    Map<String, Object> data = new HashMap<>();
    data.put("Full Name", fullName);
    data.put("Industry", industry);
    data.put("Organization Name", orgName);
    data.put("Email", email);
    data.put("API Key", API_KEY_HASH);
    ApiFuture<WriteResult> result = docRef.set(data);
    System.out.println("Update time : " + result.get().getUpdateTime());
    lastAPIKey = API_KEY_HASH;
    return API_KEY_HASH;
  }

  String addCreditCardPayoffAPICall(
      String IPAddr,
      String timeStamp,
      CreditCardPayoff creditCardPayoff,
      HashMap<String, Double> response)
      throws Exception {
    DocumentReference docRef = db.collection("API-Calls").document(timeStamp);
    Map<String, Object> data = new HashMap<>();
    data.put("IP Address", IPAddr);
    data.put("timeStamp", timeStamp);
    data.put("balance", creditCardPayoff.creditCardBalance);
    data.put("interest rate", creditCardPayoff.creditCardInterestRate);
    data.put("months", creditCardPayoff.numMonthsToPayOff);
    data.put("monthly-payment", response.get("monthly-payment"));
    data.put("total-principle-paid", response.get("total-principle-paid"));
    data.put("total-interest-paid", response.get("total-interest-paid"));
    ApiFuture<WriteResult> result = docRef.set(data);
    System.out.println("Update time : " + result.get().getUpdateTime());
    lastTS = timeStamp;
    return "Added";
  }

  String addCalculateSavingsAPICall(
      String IPAddr,
      String timeStamp,
      SimpleSavingsCalculator simpleSavingsCalculator,
      HashMap<String, Float> response)
      throws Exception {
    DocumentReference docRef = db.collection("API-Calls").document(timeStamp);
    Map<String, Object> data = new HashMap<>();
    data.put("IP Address", IPAddr);
    data.put("timeStamp", timeStamp);
    data.put("deposit", simpleSavingsCalculator.intialDeposit);
    data.put("monthly contribution", simpleSavingsCalculator.monthlyContribution);
    data.put("years", simpleSavingsCalculator.years);
    data.put("interest", simpleSavingsCalculator.simpleInterest);
    data.put("savings-total", response.get("savings-total"));
    data.put("total-contributions", response.get("total-contributions"));
    data.put("interest-earned", response.get("interest-earned"));
    ApiFuture<WriteResult> result = docRef.set(data);
    System.out.println("Update time : " + result.get().getUpdateTime());
    lastTS = timeStamp;
    return "Added";
  }

  String addCalculateMinPaymentAPICall(
      String IPAddr,
      String timeStamp,
      MinimumPaymentCalculator minimumPaymentCalculator,
      HashMap<String, Float> response)
      throws Exception {
    DocumentReference docRef = db.collection("API-Calls").document(timeStamp);
    Map<String, Object> data = new HashMap<>();
    data.put("IP Address", IPAddr);
    data.put("timeStamp", timeStamp);
    data.put("balance", minimumPaymentCalculator.creditCardBalance);
    data.put("interest rate", minimumPaymentCalculator.creditCardInterestRate);
    data.put("percentage", minimumPaymentCalculator.minPaymentPercentage);
    data.put("monthly-payment", response.get("monthly-payment"));
    data.put("number-months", response.get("number-months"));
    data.put("amount-paid", response.get("amount-paid"));
    ApiFuture<WriteResult> result = docRef.set(data);
    System.out.println("Update time : " + result.get().getUpdateTime());
    lastTS = timeStamp;
    return "Added";
  }

  String addCDCalculatorAPICall(
      String IPAddr, String timeStamp, CDCalculator cdCalculator, HashMap<String, Float> response)
      throws Exception {
    DocumentReference docRef = db.collection("API-Calls").document(timeStamp);
    Map<String, Object> data = new HashMap<>();
    data.put("IP Address", IPAddr);
    data.put("timeStamp", timeStamp);
    data.put("deposit", cdCalculator.deposit);
    data.put("years", cdCalculator.years);
    data.put("apy", cdCalculator.apy);
    data.put("total-balance", response.get("total-balance"));
    data.put("total-interest-earned", response.get("total-interest-earned"));
    ApiFuture<WriteResult> result = docRef.set(data);
    System.out.println("Update time : " + result.get().getUpdateTime());
    lastTS = timeStamp;
    return "Added";
  }

  String revokeKey(String API_KEY) throws Exception {
    System.out.println("########## Revoking Key ##########");
    ApiFuture<QuerySnapshot> docs =
        db.collection("users").whereEqualTo("API Key", API_KEY).limit(1).get();
    List<QueryDocumentSnapshot> documents = docs.get().getDocuments();
    if (documents.isEmpty()) {
      return "No user exists in the database with that key.";
    }
    for (DocumentSnapshot document : documents) {
      DocumentReference docRef = document.getReference();
      ApiFuture<WriteResult> result = docRef.update("API Key", null);
      System.out.println("API Key: " + API_KEY);
      System.out.println("Update time : " + result.get().getUpdateTime());
    }
    return "Deleted key " + API_KEY + " from database.";
  }

  Boolean validateAPIKey(String apiKey) throws Exception {
    ApiFuture<QuerySnapshot> docs =
        db.collection("users").whereEqualTo("API Key", apiKey).limit(1).get();
    List<QueryDocumentSnapshot> documents = docs.get().getDocuments();
    if (documents.isEmpty()) {
      return false;
    }
    return true;
  }
}
