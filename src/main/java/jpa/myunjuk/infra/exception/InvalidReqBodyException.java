package jpa.myunjuk.infra.exception;

public class InvalidReqBodyException extends CustomRuntimeException{

    public InvalidReqBodyException(String msg){
        super(msg);
        name = "InvalidReqParamException";
    }
}
