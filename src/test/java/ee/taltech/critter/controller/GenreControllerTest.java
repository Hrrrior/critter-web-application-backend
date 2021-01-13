package ee.taltech.critter.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", password = "test", roles = "USER")
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getGenre() throws Exception {
        mvc.perform(get("/genres").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(24)));
    }

    @Test
    void saveGenre() throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", 0);
        json.put("name", "Souls-like");

        mvc.perform(post("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("Souls-like"));
    }

    @Test
    void saveGenresFromIgdb() throws Exception {
        mvc.perform(post("/genres/igdb").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(23)));
    }

    @Test
    void deleteGenre() throws Exception {
        mvc.perform(delete("/genres/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
