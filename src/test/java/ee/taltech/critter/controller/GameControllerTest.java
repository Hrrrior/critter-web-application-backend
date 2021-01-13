package ee.taltech.critter.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", password = "test", roles = "ADMIN")
class GameControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getGame() throws Exception {
        RequestBuilder request = get("/games").accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void testGetGame() throws Exception {
        RequestBuilder request = get("/games/359").accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(359))
                .andExpect(jsonPath("$.name").value("Final Fantasy XV"));
    }

    @Test
    void saveGame() throws Exception {
        mvc.perform(post("/igdb")
                .contentType(MediaType.APPLICATION_JSON)
                .content("minecraft")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("135400"))
                .andExpect(jsonPath("$.name").value("Minecraft"));
    }

    @Test
    void saveGameBad() throws Exception {
        mvc.perform(post("/igdb")
                .contentType(MediaType.APPLICATION_JSON)
                .content("dkapsdksapkdaspokaspdkapsdkdspdakpasdkpasdkpadkspksaddkpaskpoasdposakpoda")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveAllGame() throws Exception {
        mvc.perform(post("/igdb/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("gta")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void forbiddenUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/igdb").accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteGame() throws Exception {
        mvc.perform(delete("/games/359").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGameBad() throws Exception {
        mvc.perform(delete("/games/55235345345543543345").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
