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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestBeans.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserAuthRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void signUpWithValidRequestReturnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "Иван",
                                "lastName": "Иванов",
                                "middleName": "Иванович",
                                "email": "example@gmail.com",
                                "password": "123456"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("example@gmail.com"));
    }

    @Test
    @Order(2)
    public void signUpWithInvalidEmailReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Олег",
                        "lastName": "Олегов",
                        "middleName": "Олегович",
                        "email": "dshvbshjvbsb",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void signUpWithExistingEmailReturnsUnprocessableEntity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstName": "Иван",
                                "lastName": "Иванов",
                                "middleName": "Иванович",
                                "email": "example@gmail.com",
                                "password": "123456"
                            }
                        """))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Order(4)
    public void LoginWithValidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "example@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("example@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Иван"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Иванов"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middleName").value("Иванович"));
    }

}
