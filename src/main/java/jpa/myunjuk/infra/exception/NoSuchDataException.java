package jpa.myunjuk.infra.exception;

import lombok.Getter;

public class NoSuchDataException extends RuntimeException{

    @Getter
    private final String NAME;

    public NoSuchDataException(String msg){
        super(msg);
        NAME = "NoSuchDataException";
    }
}
