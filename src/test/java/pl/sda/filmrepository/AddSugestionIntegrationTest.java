package pl.sda.filmrepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddSugestionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("gdy wyślemy GET na /api/suggestions dotaniemy status OK")
    @Test
    void test1() throws Exception {
        mockMvc.perform(get("/api/suggestions"))
                .andExpect(status().isOk());
    }
}
