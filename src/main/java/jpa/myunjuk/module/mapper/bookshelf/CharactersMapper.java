package jpa.myunjuk.module.mapper.bookshelf;

import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CharactersMapper {
    CharactersMapper INSTANCE = Mappers.getMapper(CharactersMapper.class);

    AddSearchDetailResDto toDto(Characters characters);
}
