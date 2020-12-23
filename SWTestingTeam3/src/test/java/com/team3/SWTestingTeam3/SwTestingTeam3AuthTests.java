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
class SwTestingTeam3AuthTests {
    @Autowired private MockMvc mvc;

    @Test
    void cdAuthTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/cd-calculator")
                        .param("apiKey", "")
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
                                            + "    \"Invalid API key!\": 401.0\n"
                                            + "}"
                            )
                );
    }

    @Test
    void ccMinAuthTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/credit-card-min-payment-calculator")
                        .param("apiKey", "")
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
                                                + "    \"Invalid API key!\": 401.0\n"
                                                + "}"
                                )
                );
    }

    @Test
    void ssAuthTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/simple-savings-calculator")
                        .param("apiKey", "")
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
                                                + "    \"Invalid API key!\": 401.0\n"
                                                + "}"
                                )
                );
    }

    @Test
    void ccPayoffAuthTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/credit-card-payoff")
                        .param("apiKey", "")
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
                                                + "    \"Invalid API key!\": 401.0\n"
                                                + "}"
                                )
                );
    }

    @Test
    void revokeKeyTest() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/revokeKey")
                        .param("apiKey", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(
                        MockMvcResultMatchers.content().string("No user exists in the database with that key.")
                );
    }
}
