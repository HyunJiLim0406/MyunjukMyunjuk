package jpa.myunjuk.infra.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import jpa.myunjuk.module.model.domain.BookStatus;

public class BookStatusConverter implements Converter<String, BookStatus> {
    @Override
    public BookStatus convert(String value) {
        return BookStatus.valueOf(value.toUpperCase());
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
