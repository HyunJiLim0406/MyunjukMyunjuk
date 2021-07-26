package jpa.myunjuk.module.model.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BookStatus {
    DONE, READING, WISH;

    @JsonCreator
    public static BookStatus from(String value){
        return BookStatus.valueOf(value.toUpperCase());
    }
}