package develop.backend.Club_card.controller;

import develop.backend.Club_card.TestBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestBeans.class)
@AutoConfigureMockMvc
public class UserAuthController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signUpWithValidRequestReturnsValidData() throws Exception {
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
    @Sql("/sql/add_user.sql")
    public void signUpWithExistingEmailReturnsUnprocessableEntity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Игорь",
                        "lastName": "Николаев",
                        "middleName": "Игоревич",
                        "email": "example@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void signUpAndLoginWithValidRequest() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "ivanov@gmail.com",
                        "password": "123456"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ivanov@gmail.com"));
    }

}
