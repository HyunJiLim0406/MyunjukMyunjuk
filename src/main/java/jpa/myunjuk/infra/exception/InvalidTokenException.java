package jpa.myunjuk.infra.exception;

public class InvalidTokenException extends CustomRuntimeException{

    public InvalidTokenException(String msg){
        super(msg);
        name = "InvalidTokenException";
    }
}
