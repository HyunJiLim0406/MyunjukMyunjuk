package jpa.myunjuk.infra.exception;

public class InvalidJwtTokenException extends CustomRuntimeException{

    public InvalidJwtTokenException(String msg){
        super(msg);
        name = "InvalidJwtTokenException";
    }
}
