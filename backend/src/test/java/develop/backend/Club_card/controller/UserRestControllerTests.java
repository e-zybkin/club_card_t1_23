package develop.backend.Club_card.controller;

import develop.backend.Club_card.TestBeans;
import org.junit.jupiter.api.*;
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
public class UserRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void loginUserWithValidRequestAndGetUserData() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/club-card/api/users")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ivanov@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Иван"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Иванов"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middleName").value("Иванович"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_MEMBER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.privilege").value("PRIVILEGE_STANDARD"));
    }

    @Test
    @Order(2)
    public void loginUserAndUpdateUserDataWithValidRequest() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.patch("/club-card/api/users")
            .header("Authorization", "Bearer " + bearerToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                   "email": "sergeev@gmail.com",
                   "firstName": "Сергей",
                   "lastName": "Сергеев",
                   "middleName": "Сергеевич",
                   "dateOfBirth": "2005-10-02"
               }
            """))
            .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void loginUserAndUpdateUserDataWithInvalidRequest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "sergeev@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String bearerToken = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.patch("/club-card/api/users")
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                   "email": "sergeev@gmail.com",
                   "firstName": "          ",
                   "lastName": "Сергеев",
                   "middleName": "Сергеевич",
                   "dateOfBirth": "2005-10-02"
               }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    public void loginUserAndMakeDeletionRequestReturnsOkStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "sergeev@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String bearerToken = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/users")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void loginUserReturnsForbidden() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "sergeev@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isForbidden())
                .andReturn();
    }

}
