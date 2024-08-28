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

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void signUpWithValidRequestReturnsValidMessage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                            {
//                                "username": "Sandy",
//                                "password": "12345",
//                                "email": "sandy@gmail.com"
//                            }
//                        """))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Sandy"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("sandy@gmail.com"));
//    }
//
//    @Test
//    public void signUpWithInvalidEmailReturnsBadRequest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                            {
//                                "username": "Sandy",
//                                "password": "12345",
//                                "email": "invalidEmail"
//                            }
//                        """))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @Sql("/sql/user.sql")
//    public void signUpUserWithExistingUsernameReturnsUnprocessableEntity() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/club-card/api/auth/signup")
//                .contentType(Me))
//    }

}
