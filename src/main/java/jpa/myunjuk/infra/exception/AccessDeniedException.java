package jpa.myunjuk.infra.exception;

public class AccessDeniedException extends CustomRuntimeException{

    public AccessDeniedException(String msg){
        super(msg);
        name = "AccessDeniedException";
    }
}
