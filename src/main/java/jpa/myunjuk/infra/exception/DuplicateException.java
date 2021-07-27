package jpa.myunjuk.infra.exception;

public class DuplicateException extends CustomRuntimeException{

    public DuplicateException(String msg){
        super(msg);
        name = "DuplicateException";
    }
}
