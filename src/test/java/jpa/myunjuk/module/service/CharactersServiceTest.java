package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.domain.UserCharacter;
import jpa.myunjuk.module.repository.UserCharacterRepository;
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
    @Autowired
    UserCharacterRepository userCharacterRepository;

    @Test
    @DisplayName("User character list | Success")
    void userCharacterListSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        List<UserCharacterDto> userCharacterDtos = charactersService.userCharacterList(user);
        assertEquals(user.getUserCharacters().size(), userCharacterDtos.size());
        assertTrue(userCharacterDtos.get(0).isRepresentation());
    }

    @Test
    @DisplayName("Update Representation | Success")
    void updateRepresentationSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        //기존 대표 캐릭터
        UserCharacter oldRepresentation = userCharacterRepository.findById(2L).orElse(null);
        assertNotNull(oldRepresentation);
        assertTrue(oldRepresentation.isRepresentation());

        //새로운 대표 캐릭터
        UserCharacter newRepresentation = userCharacterRepository.findById(44L).orElse(null);
        assertNotNull(newRepresentation);
        assertFalse(newRepresentation.isRepresentation());

        charactersService.updateCharacterRepresentation(user, 44L);
        assertFalse(oldRepresentation.isRepresentation());
        assertTrue(newRepresentation.isRepresentation());
    }

    @Test
    @DisplayName("Update Representation | Fail : No Such Data")
    void updateRepresentationFailNoSuchData() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        NoSuchDataException e = assertThrows(NoSuchDataException.class, () ->
                charactersService.updateCharacterRepresentation(user, 10000L));
        assertEquals("User character id = 10000", e.getMessage());
    }

    @Test
    @DisplayName("Update Representation | Fail : Access Deny")
    void updateRepresentationFailAccessDeny() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        AccessDeniedException e = assertThrows(AccessDeniedException.class, () ->
                charactersService.updateCharacterRepresentation(user, 87L));
        assertEquals("User character id = 87", e.getMessage());
    }
}