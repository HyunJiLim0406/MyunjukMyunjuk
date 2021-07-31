package jpa.myunjuk.infra.exception;

public class S3Exception extends CustomRuntimeException{

    public S3Exception(String msg){
        super(msg);
        name = "S3Exception";
    }
}
