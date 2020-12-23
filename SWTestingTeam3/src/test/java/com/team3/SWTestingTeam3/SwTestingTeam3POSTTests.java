package com.team3.SWTestingTeam3;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.text.DecimalFormat;

@SpringBootTest
@AutoConfigureMockMvc
class SwTestingTeam3POSTTests {
  @Autowired private MockMvc mvc;

  private FireStore fs;

  private String apiKey;

  private ApiFuture<QuerySnapshot> docs;

  private List<QueryDocumentSnapshot> documents;

  @BeforeEach
  void init() throws Exception {
    fs = new FireStore();
    apiKey = fs.addUser("myOrgName", "myIndustry", "myFull Name", "my@email.com");
  }

  @Test
  void cdPOSTTest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/cd-calculator")
                .param("apiKey", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"deposit\": 10000,\n"
                        + "    \"years\": 5,\n"
                        + "    \"apy\": 2.5\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\n"
                        + "    \"total-balance\": 11314.08,\n"
                        + "    \"total-interest-earned\": 1314.08\n"
                        + "}"));

    docs = fs.getDb().collection("users").whereEqualTo("API Key", apiKey).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write user to database");

    docs = fs.getDb().collection("API-Calls").whereEqualTo("timeStamp", fs.getLastTS()).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write req/res to database");
  }

  @Test
  void ccMinPOSTTest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/credit-card-min-payment-calculator")
                .param("apiKey", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"creditCardBalance\": 100,\n"
                        + "    \"creditCardInterestRate\": 2,\n"
                        + "    \"minPaymentPercentage\": 2\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\n"
                        + "    \"amount-paid\": 102.0,\n"
                        + "    \"monthly-payment\": 2.0,\n"
                        + "    \"number-months\": 50.0\n"
                        + "}"));

    docs = fs.getDb().collection("users").whereEqualTo("API Key", apiKey).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write user to database");

    docs = fs.getDb().collection("API-Calls").whereEqualTo("timeStamp", fs.getLastTS()).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write req/res to database");
  }

  @Test
  void ssPOSTTest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/simple-savings-calculator")
                .param("apiKey", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"intialDeposit\": 1000,\n"
                        + "    \"monthlyContribution\": 20,\n"
                        + "    \"years\": 2,\n"
                        + "    \"simpleInterest\": 2.5\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\n"
                        + "    \"savings-total\": 1548.775,\n"
                        + "    \"total-contributions\": 480.0,\n"
                        + "    \"interest-earned\": 68.775024\n"
                        + "}"));

    docs = fs.getDb().collection("users").whereEqualTo("API Key", apiKey).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write user to database");

    docs = fs.getDb().collection("API-Calls").whereEqualTo("timeStamp", fs.getLastTS()).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write req/res to database");
  }

  @Test
  void ccPayoffPOSTTest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/credit-card-payoff")
                .param("apiKey", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"creditCardBalance\": 1000,\n"
                        + "    \"creditCardInterestRate\": 4,\n"
                        + "    \"numMonthsToPayOff\": 15\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\n"
                        + "    \"monthly-payment\": 69.33333333333333,\n"
                        + "    \"total-principle-paid\": 1000.0,\n"
                        + "    \"total-interest-paid\": 40.0\n"
                        + "}"));

    docs = fs.getDb().collection("users").whereEqualTo("API Key", apiKey).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write user to database");

    docs = fs.getDb().collection("API-Calls").whereEqualTo("timeStamp", fs.getLastTS()).limit(1).get();
    documents = docs.get().getDocuments();

    Assertions.assertFalse(documents.isEmpty(), "did not write req/res to database");
  }
}
