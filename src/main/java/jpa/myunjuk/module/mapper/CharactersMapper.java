package jpa.myunjuk.module.mapper;

import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static jpa.myunjuk.module.model.dto.CharacterDtos.*;

@Mapper(componentModel = "spring")
public interface CharactersMapper {
    CharactersMapper INSTANCE = Mappers.getMapper(CharactersMapper.class);

    AddSearchDetailResDto toAddSearchDetailResDto(Characters characters);
    CharacterListDto toCharacterListDto(Characters characters);
    CharacterDto toCharacterDto(Characters characters);
}
