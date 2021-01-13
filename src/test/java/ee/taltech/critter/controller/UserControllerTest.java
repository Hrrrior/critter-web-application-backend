package ee.taltech.critter.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", password = "test", roles = "ADMIN")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

//    @BeforeEach
    @Test
    void register() throws Exception {
        JSONObject json = new JSONObject();
        json.put("username", "bob");
        json.put("password", "bob");

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    void registerBadUser() throws Exception {
        JSONObject json = new JSONObject();
        json.put("username", "");
        json.put("password", "bob");

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void registerBadPass() throws Exception {
        JSONObject json = new JSONObject();
        json.put("username", "bob");
        json.put("password", "");

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loginBadUser() throws Exception {
        JSONObject json = new JSONObject();
        json.put("username", "");
        json.put("password", "bob");

        mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loginBadPass() throws Exception {
        JSONObject json = new JSONObject();
        json.put("username", "bob");
        json.put("password", "");

        mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
