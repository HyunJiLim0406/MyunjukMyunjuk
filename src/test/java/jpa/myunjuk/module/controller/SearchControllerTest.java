package jpa.myunjuk.module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@DisplayName(value = "Book Search Test")
@AutoConfigureMockMvc(addFilters = false)
class SearchControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName(value = "Search | Success")
    void searchSuccess() throws Exception {
        mockMvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "해리포터")
                .param("start", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Search | Fail : Missing keyword")
    void searchFailKeyword() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("keyword", "Required request parameter 'keyword' for method parameter type String is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName(value = "Search | Fail : Missing start")
    void searchFailStart() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("start", "Required request parameter 'start' for method parameter type int is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "해리포터"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName(value = "Search Detail | Success")
    void searchDetailSuccess() throws Exception {
        mockMvc.perform(get("/search/detail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isbn", "8952733789 9788952733788"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "Search Detail | Fail : Missing isbn")
    void searchDetailFailIsbn() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("isbn", "Required request parameter 'isbn' for method parameter type String is not present");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search/detail")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName(value = "Search Detail | Fail : Invalid isbn(Data)")
    void searchDetailFailInvalidIsbnData() throws Exception{
        Map<String, String> error = new HashMap<>();
        error.put("InvalidReqParamException", "isbn = djfkejf djfkejkf");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search/detail")
        .param("isbn", "djfkejf djfkejkf")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName(value = "Search Detail | Fail : Invalid isbn(Format)")
    void searchDetailFailInvalidIsbnFormat() throws Exception{
        Map<String, String> error = new HashMap<>();
        error.put("InvalidReqParamException", "isbn = 8952733789 ");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/search/detail")
                .param("isbn", "8952733789 ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }
}