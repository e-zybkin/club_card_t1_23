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
public class UserManagerRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void signUpSuperAdminThenLoginThenGetAllUsersReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Иван",
                        "lastName": "Иванов",
                        "middleName": "Иванович",
                        "email": "superadmin@yandex.ru",
                        "password": "superadminpassword"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("superadmin@yandex.ru"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "superadmin@yandex.ru",
                        "password": "superadminpassword"
                    }
                 """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("superadmin@yandex.ru"))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String bearerToken = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.get("/club-card/api/manager/get/users")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk());
    }

//    @Test
//    @Order(2)
//    public void signUpNewUserThenMakeHimManagerAndHighPrivilege() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                    {
//                        "firstName": "Сергей",
//                        "lastName": "Сергеев",
//                        "middleName": "Сергеевич",
//                        "email": "example@yandex.ru",
//                        "password": "1234567"
//                    }
//                """))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("superadmin@yandex.ru"));
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                    {
//                        "email": "superadmin@yandex.ru",
//                        "password": "superadminpassword"
//                    }
//                 """))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("superadmin@yandex.ru"))
//                .andReturn();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String responseBody = result.getResponse().getContentAsString();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        String bearerToken = jsonNode.get("token").asText();
//    }
}
