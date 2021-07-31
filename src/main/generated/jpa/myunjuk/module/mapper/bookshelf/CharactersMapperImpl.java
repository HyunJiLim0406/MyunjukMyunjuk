package jpa.myunjuk.module.mapper.bookshelf;

import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto.AddSearchDetailResDtoBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-31T15:09:38+0900",
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
}
