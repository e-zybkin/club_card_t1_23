package develop.backend.Club_card.controller;

import develop.backend.Club_card.TestBeans;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestBeans.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserCardRestControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void signUpThenLoginThenCreateCardForUserReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                         "firstName": "Иван",
                         "lastName": "Иванов",
                         "middleName": "Иванович",
                         "email": "ivanov@gmail.com",
                         "password": "123456"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ivanov@gmail.com"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "ivanov@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String bearerToken = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/card/create")
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "colour": "BLUE",
                        "pattern": "FULL"
                    }
                """))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void signUpThenLoginThenCreateCardForUserReturnsFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                         "firstName": "Исаев",
                         "lastName": "Иванов",
                         "middleName": "Иванович",
                         "email": "isaev@gmail.com",
                         "password": "123456"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ivanov@gmail.com"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "ivanov@gmail.com",
                        "password": "123456"
                    }
                """))
            .andExpect(status().isOk())
            .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String bearerToken = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/card/create")
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "colour": "YELLOW",
                        "pattern": "FULL"
                    }
                """))
            .andExpect(status().isBadRequest());
    }

}
