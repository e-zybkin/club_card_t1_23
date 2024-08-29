package develop.backend.Club_card.controller;

import develop.backend.Club_card.TestBeans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@AutoConfigureMockMvc
public class UserController {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void signUpClubMemberWithValidRequestReturnsValidMessage() throws Exception {
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
    }

    @Test
    public void loginUserWithValidRequestAndGetUserData() throws Exception {
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
                   "id": 1,
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
    public void loginUserAndUpdateUserDataWithInvalidRequest() throws Exception {
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
                   "id": 1,
                   "email": "sergeev@gmail.com",
                   "lastName": "Сергеев",
                   "middleName": "Сергеевич",
                   "dateOfBirth": "2005-10-02"
               }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginUserAndMakeDeletionRequestReturnsOkStatus() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/users")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk());
    }

}
