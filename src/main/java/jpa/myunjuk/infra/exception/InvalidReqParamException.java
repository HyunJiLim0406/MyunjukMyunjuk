package jpa.myunjuk.infra.exception;

import lombok.Getter;

public class InvalidReqParamException extends RuntimeException{

    @Getter
    private final String NAME;

    public InvalidReqParamException(String msg){
        super(msg);
        NAME = "InvalidReqParamException";
    }
}
