package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.domain.UserCharacter;
import jpa.myunjuk.module.repository.CharactersRepository;
import jpa.myunjuk.module.repository.UserCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CharactersService {

    private final UserCharacterRepository userCharacterRepository;
    private final CharactersRepository charactersRepository;

    public Characters addNewCharacters(User user, List<Characters> charactersList) {
        double maxHeight = -1.0;
        List<Characters> userCharacters = getCharactersFromUser(user);
        for (Characters characters : charactersList) {
            if (!userCharacters.contains(characters)) {
                maxHeight = Math.max(maxHeight, characters.getHeight());
                userCharacterRepository.save(UserCharacter.builder()
                        .user(user)
                        .characters(characters)
                        .achieve(LocalDate.now())
                        .representation(false)
                        .build());
            }
        }
        return charactersRepository.findByHeight(maxHeight).orElse(null);
    }

    private List<Characters> getCharactersFromUser(User user) {
        List<Characters> result = new ArrayList<>();
        for (UserCharacter userCharacter : user.getUserCharacters())
            result.add(userCharacter.getCharacters());
        return result;
    }
}
