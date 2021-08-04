package jpa.myunjuk.module.mapper;

import jpa.myunjuk.module.model.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static jpa.myunjuk.module.model.dto.HomeDto.*;

@Mapper(componentModel = "spring")
public interface HomeMapper {
    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    Item toItem(Book book);
}
