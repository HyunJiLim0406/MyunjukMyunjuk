package jpa.myunjuk.module.mapper;

import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.dto.CharacterDtos.CharacterListDto;
import jpa.myunjuk.module.model.dto.CharacterDtos.CharacterListDto.CharacterListDtoBuilder;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto.AddSearchDetailResDtoBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-04T15:32:10+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class CharactersMapperImpl implements CharactersMapper {

    @Override
    public AddSearchDetailResDto toAddSearchDetailResDto(Characters characters) {
        if ( characters == null ) {
            return null;
        }

        AddSearchDetailResDtoBuilder addSearchDetailResDto = AddSearchDetailResDto.builder();

        addSearchDetailResDto.id( characters.getId() );
        addSearchDetailResDto.name( characters.getName() );
        addSearchDetailResDto.img( characters.getImg() );

        return addSearchDetailResDto.build();
    }

    @Override
    public CharacterListDto toCharacterListDto(Characters characters) {
        if ( characters == null ) {
            return null;
        }

        CharacterListDtoBuilder characterListDto = CharacterListDto.builder();

        characterListDto.id( characters.getId() );
        characterListDto.name( characters.getName() );
        characterListDto.img( characters.getImg() );
        characterListDto.shortDescription( characters.getShortDescription() );

        return characterListDto.build();
    }
}
