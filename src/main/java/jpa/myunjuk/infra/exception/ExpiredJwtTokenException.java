package jpa.myunjuk.infra.exception;

public class ExpiredJwtTokenException extends CustomRuntimeException{

    public ExpiredJwtTokenException(String msg){
        super(msg);
        name = "ExpiredJwtTokenException";
    }
}
