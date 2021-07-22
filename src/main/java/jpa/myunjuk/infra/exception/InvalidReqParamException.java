package jpa.myunjuk.infra.exception;

public class InvalidReqParamException extends CustomRuntimeException{

    public InvalidReqParamException(String msg){
        super(msg);
        name = "InvalidReqParamException";
    }
}
