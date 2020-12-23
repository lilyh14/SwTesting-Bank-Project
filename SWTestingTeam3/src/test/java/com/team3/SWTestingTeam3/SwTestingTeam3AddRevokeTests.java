package com.team3.SWTestingTeam3;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SwTestingTeam3AddRevokeTests {
    @Autowired private MockMvc mvc;

    private FireStore fs;

    private ApiFuture<QuerySnapshot> docs;

    private List<QueryDocumentSnapshot> documents;

    @BeforeEach
    void init() throws Exception {
        fs = new FireStore();
    }

    @Test
    void addUserTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/firestoreAuth")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n"
                                        + "    \"orgName\": \"myOrg\",\n"
                                        + "    \"industry\": \"myIndustry\",\n"
                                        + "    \"fullName\": \"MyFull Name\", \n"
                                        + "    \"email\": \"myFull@email.com\" \n"
                                        + "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.content().string(fs.getLastAPIKey())
                );

        docs = fs.getDb().collection("users").whereEqualTo("API Key", fs.getLastAPIKey()).limit(1).get();
        documents = docs.get().getDocuments();

        Assertions.assertFalse(documents.isEmpty(), "did not write user to database");
    }

    @Test
    void revokeKeyTest() throws Exception {
        fs.addUser("myOrgName", "myIndustry", "myFull Name", "my@email.com");

        mvc.perform(
                MockMvcRequestBuilders.post("/revokeKey")
                        .param("apiKey", fs.getLastAPIKey()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(
                        MockMvcResultMatchers.content().string("Deleted key " + fs.getLastAPIKey() + " from database.")
                );

        docs = fs.getDb().collection("users").whereEqualTo("API Key", fs.getLastAPIKey()).limit(1).get();
        documents = docs.get().getDocuments();

        Assertions.assertTrue(documents.isEmpty(), "did not revoke the API key for this user");
    }
}