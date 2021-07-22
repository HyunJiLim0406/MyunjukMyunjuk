package jpa.myunjuk.infra.exception;

public class NoSuchDataException extends CustomRuntimeException{

    public NoSuchDataException(String msg){
        super(msg);
        name = "NoSuchDataException";
    }
}
