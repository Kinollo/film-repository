package pl.sda.filmrepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.filmrepository.model.Suggestion;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddSugestionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

    @DisplayName("gdy wyślemy POST na /api/suggestions" +
            "dodajemy kilka sugesti i znajdujemy sugestie danego autora")
    @Test
    void test3() throws Exception {
        String suggestionJson1="{\"title\":\"Rambo1\", \"link\":\"www.rambo1.com\", \"score\":5}";
        String suggestionJson2="{\"title\":\"Rambo2\", \"link\":\"www.rambo2.com\", \"score\":4}";
        String suggestionJson3="{\"title\":\"Rambo3\", \"link\":\"www.rambo3.com\", \"score\":1}";

        SecurityContext securityContext = SecurityContextHolder.getContext();

        withMockUser(securityContext, "user1");
        addSuggestions(suggestionJson1);
        addSuggestions(suggestionJson2);
        withMockUser(securityContext, "gosc");
        addSuggestions(suggestionJson3);

        mockMvc.perform(get("/api/suggestions").param("author", "user1"))
                .andExpect(jsonPath("$",hasSize(2)));
    }

    private void withMockUser(SecurityContext securityContext, String username) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, "password", Arrays.asList(new SimpleGrantedAuthority("USER")));
        securityContext.setAuthentication(authentication);
    }

    Long addSuggestions(String suggestion) throws Exception {
        String createSuggestionAsJson = mockMvc.perform(
                post("/api/suggestions")
                        .content(suggestion)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(createSuggestionAsJson, Suggestion.class).getId();

    }

    @DisplayName("gdy wyślemy GET na /api/suggestions" +
            "znajdujemy sugestie po ID")
    @Test
    void test4() throws Exception {
        String suggestionJson1="{\"title\":\"Rambo1\", \"link\":\"www.rambo1.com\", \"score\":5}";
        String suggestionJson2="{\"title\":\"Rambo2\", \"link\":\"www.rambo2.com\", \"score\":4}";
        String suggestionJson3="{\"title\":\"Rambo3\", \"link\":\"www.rambo3.com\", \"score\":1}";

        Long id = addSuggestions(suggestionJson1);
        addSuggestions(suggestionJson2);
        addSuggestions(suggestionJson3);

        mockMvc.perform(get("/api/suggestions/{id}",id))
                .andExpect(jsonPath("$.title",is("Rambo1")));
    }
    @DisplayName("gdy wyślemy DELETE na /api/suggestions/{id}" +
            "usuniemy sugestie po ID")
    @Test
    void test5() throws Exception {
        String suggestionJson1="{\"title\":\"Rambo1\", \"link\":\"www.rambo1.com\", \"score\":5}";
        Long id = addSuggestions(suggestionJson1);

        mockMvc.perform(delete("/api/suggestions/{id}", id))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/suggestions"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("gdy wyślemy POST na /api/suggestions bez podawania autora" +
            "to zostanie on ustawiony na użytkownika, który aktualnie wykonuje operacje")
    @Test
    @WithMockUser(username = "goobar")
    void test6() throws Exception {
        String suggestionJson1="{\"title\":\"Rambo1\", \"link\":\"www.rambo1.com\", \"score\":5}";
        Long id = addSuggestions(suggestionJson1);

        mockMvc.perform(get("/api/suggestions/{id}", id))
                .andExpect(jsonPath("$.author",is("goobar")));
    }
}
