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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static jpa.myunjuk.module.model.dto.CharacterDtos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@DisplayName("Characters Controller Test")
@AutoConfigureMockMvc(addFilters = false)
class CharactersControllerTest {

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
    @DisplayName("Retrieve character list | Success")
    void characterListSuccess() throws Exception {
        mockMvc.perform(get("/character")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Retrieve character | Success")
    void characterSuccess() throws Exception {
        CharacterDto dto = CharacterDto.builder()
                .id(1L)
                .name("사는가")
                .img("https://drive.google.com/uc?export=view&id=1nCEX42C4w9-09kaxE9ZP5vcM7z4Ir9Ju")
                .birthday(LocalDate.parse("1990-01-01"))
                .height(0.0)
                .shortDescription("얼마나 풍부하게 커다란 것은 약동하다. 뜨거운지라")
                .longDescription("이상이 싹이 보이는 열락의 무엇을 그리하였는가? 할지니, 물방아 것은 그들은 바로 사라지지 갑 방황하였으며, 것이다. " +
                        "이성은 청춘의 생의 길을 그들의 곧 무엇이 심장의 아름다우냐? 열락의 새 위하여서 봄바람을 못하다 거친 청춘의 있으랴? " +
                        "많이 그것은 꽃 우리 품에 그리하였는가? 것은 옷을 눈이 별과 이것이다.")
                .build();
        String response = objectMapper.writeValueAsString(dto);

        mockMvc.perform(get("/character/detail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("characterId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("Retrieve character | Fail : No such data")
    void characterFailNoSuchDate() throws Exception {
        Map<String, String> error = new HashMap<>();
        error.put("NoSuchDataException", "Character id = 100");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(get("/character/detail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("characterId", "100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }
}