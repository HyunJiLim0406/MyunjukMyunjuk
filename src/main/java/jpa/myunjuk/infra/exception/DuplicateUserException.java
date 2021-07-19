package jpa.myunjuk.infra.exception;

import lombok.Getter;

public class DuplicateUserException extends RuntimeException{

    @Getter
    private final String NAME;

    public DuplicateUserException(String msg){
        super(msg);
        NAME = "DuplicateUserException";
    }
}
