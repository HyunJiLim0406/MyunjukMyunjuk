package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.mapper.CharactersMapper;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.domain.UserCharacter;
import jpa.myunjuk.module.repository.CharactersRepository;
import jpa.myunjuk.module.repository.UserCharacterRepository;
import jpa.myunjuk.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static jpa.myunjuk.module.model.dto.CharacterDtos.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharactersService {

    private final UserRepository userRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final CharactersRepository charactersRepository;
    private final CharactersMapper charactersMapper;

    /**
     * addNewCharacters
     *
     * @param user
     * @return Characters
     */
    @Transactional
    public Characters addNewCharacters(User user, Integer bookPage) {
        user.stackBook(bookPage);
        userRepository.save(user);

        List<UserCharacter> list = charactersRepository.findByHeightLessThanEqualAndIdNotInOrderByHeightDesc(user.getBookHeight(), getCharactersFromUser(user)).stream()
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
    @Transactional
    public void removeCharacters(User user, Integer bookPage) {
        user.removeBook(bookPage);
        userRepository.save(user);

        double height = user.getBookHeight();
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

    /**
     * characterList
     *
     * @return List<CharacterListDto>
     */
    public List<CharacterListDto> characterList() {
        return charactersRepository.findAll().stream()
                .map(charactersMapper.INSTANCE::toCharacterListDto)
                .collect(Collectors.toList());
    }

    /**
     * characterDetail
     *
     * @param id
     * @return CharacterDto
     */
    public CharacterDto characterDetail(Long id) {
        return charactersMapper.INSTANCE.toCharacterDto(charactersRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Character id = " + id)));
    }

    /**
     * userCharacterList
     *
     * @param user
     * @return List<UserCharacterDto>
     */
    public List<UserCharacterDto> userCharacterList(User user) {
        return user.getUserCharacters().stream()
                .map(o -> UserCharacterDto.builder()
                        .userCharacterId(o.getId())
                        .characterId(o.getCharacters().getId())
                        .name(o.getCharacters().getName())
                        .img(o.getCharacters().getImg())
                        .height(o.getCharacters().getHeight())
                        .achieve(o.getAchieve())
                        .representation(o.isRepresentation())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * updateCharacterRepresentation
     *
     * @param user
     * @param id
     */
    @Transactional
    public void updateCharacterRepresentation(User user, Long id) {
        UserCharacter newRepresentation = userCharacterRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("User character id = " + id));
        if (!newRepresentation.getUser().equals(user))
            throw new AccessDeniedException("User character id = " + id);

        UserCharacter oldRepresentation = user.getUserCharacters().stream()
                .filter(UserCharacter::isRepresentation)
                .reduce((a, b) -> {
                    throw new NoSuchDataException("There are too many representation characters");
                })
                .orElseThrow(() -> new NoSuchDataException("There is no representation character"));
        oldRepresentation.updateRepresentation(false);
        newRepresentation.updateRepresentation(true);

        userCharacterRepository.save(oldRepresentation);
        userCharacterRepository.save(newRepresentation);
    }
}
