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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CharactersService {

    private final UserCharacterRepository userCharacterRepository;
    private final CharactersRepository charactersRepository;

    /**
     * addNewCharacters
     *
     * @param user
     * @return Characters
     */
    public Characters addNewCharacters(User user) {
        List<UserCharacter> list = charactersRepository.findByHeightLessThanEqualAndIdNotIn(user.bookHeight(), getCharactersFromUser(user)).stream()
                .sorted(Comparator.comparing(Characters::getHeight).reversed())
                .map(o -> UserCharacter.builder()
                        .user(user)
                        .characters(o)
                        .achieve(LocalDate.now())
                        .representation(false)
                        .build())
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            userCharacterRepository.saveAll(list);
            return list.get(0).getCharacters();
        }
        return null;
    }

    /**
     * removeCharacters
     *
     * @param user
     */
    public void removeCharacters(User user) {
        double height = user.bookHeight();
        List<UserCharacter> list = user.getUserCharacters().stream()
                .filter(o -> o.getCharacters().getHeight() > height)
                .collect(Collectors.toList());
        if (list.isEmpty())
            return;
        user.getUserCharacters().removeAll(list);
        userCharacterRepository.deleteAll(list);
    }

    private List<Long> getCharactersFromUser(User user) {
        return user.getUserCharacters().stream()
                .map(o -> o.getCharacters().getId())
                .collect(Collectors.toList());
    }
}
