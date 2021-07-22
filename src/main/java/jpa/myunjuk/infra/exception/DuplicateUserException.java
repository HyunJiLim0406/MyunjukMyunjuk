package jpa.myunjuk.infra.exception;

public class DuplicateUserException extends CustomRuntimeException{

    public DuplicateUserException(String msg){
        super(msg);
        name = "DuplicateUserException";
    }
}
