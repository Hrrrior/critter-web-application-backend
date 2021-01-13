package ee.taltech.critter.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", password = "test", roles = "ADMIN")
class ReviewControllerTest {
    @Autowired
    private MockMvc mvc;

//    @BeforeEach
//    void setUp() throws Exception {
//        mvc.perform(post("/igdb")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("terraria")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1879"))
//                .andExpect(jsonPath("$.name").value("Terraria"));
//    }

    @Test
    void saveReview() throws Exception {
        mvc.perform(post("/igdb")
                .contentType(MediaType.APPLICATION_JSON)
                .content("terraria")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1879"))
                .andExpect(jsonPath("$.name").value("Terraria"));

        JSONObject json = new JSONObject();
        json.put("id", 0);
        json.put("author", "Name");
        json.put("rating", 7);
        json.put("reviewDate", 2020);
        json.put("reviewText", "Game of the year");

        mvc.perform(post("/reviews/1879")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(7))
                .andExpect(jsonPath("$.reviewDate").value(2020));

        mvc.perform(get("/reviews").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void reviewNotFound() throws Exception {
        mvc.perform(get("/reviews/25").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
