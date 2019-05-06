package pl.sda.filmrepository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddSugestionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("gdy wyślemy GET na /api/suggestions dostaniemy status OK")
    @Test
    void test1() throws Exception {
        mockMvc.perform(get("/api/suggestions"))
                .andExpect(status().isOk());
    }

    @DisplayName("gdy wyślemy POST na /api/suggestions " +
            "dodamy sugestie do naszego repozytorium")
    @Test
    void test2() throws Exception {
        String suggestionJson="{\"title\":\"Rambo\", \"link\":\"www.rambo.com\", \"score\":5}";
        mockMvc.perform(
                post("/api/suggestions")
                        .content(suggestionJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        mockMvc.perform(get("/api/suggestions"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Rambo")));

    }
}
