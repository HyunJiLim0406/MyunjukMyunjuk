package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.CharacterDtos;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpa.myunjuk.module.model.dto.CharacterDtos.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Characters Service Test")
@Transactional
class CharactersServiceTest {

    @Autowired
    CharactersService charactersService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User character list | Success")
    void userCharacterListSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        List<UserCharacterDto> userCharacterDtos = charactersService.userCharacterList(user);
        assertEquals(user.getUserCharacters().size(), userCharacterDtos.size());
        assertTrue(userCharacterDtos.get(0).isRepresentation());
    }
}