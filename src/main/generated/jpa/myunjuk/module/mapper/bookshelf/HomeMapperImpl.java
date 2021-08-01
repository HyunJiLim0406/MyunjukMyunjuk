package jpa.myunjuk.module.mapper.bookshelf;

import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.dto.HomeDto.Item;
import jpa.myunjuk.module.model.dto.HomeDto.Item.ItemBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-01T19:26:16+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class HomeMapperImpl implements HomeMapper {

    @Override
    public Item toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        ItemBuilder item = Item.builder();

        item.title( book.getTitle() );
        item.totPage( book.getTotPage() );
        if ( book.getScore() != null ) {
            item.score( book.getScore() );
        }
        item.author( book.getAuthor() );
        item.thumbnail( book.getThumbnail() );

        return item.build();
    }
}
