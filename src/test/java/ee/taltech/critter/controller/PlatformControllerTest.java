package ee.taltech.critter.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
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
class PlatformControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getPlatform() throws Exception {
        mvc.perform(get("/platforms").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    void testGetPlatform() throws Exception {
        mvc.perform(get("/platforms/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PC (Microsoft Windows)"));
    }

    @Test
    void savePlatform() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Playstation 1");

        mvc.perform(post("/platforms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Playstation 1"));
    }

    @Test
    void deletePlatform() throws Exception {
        mvc.perform(delete("/platforms/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
