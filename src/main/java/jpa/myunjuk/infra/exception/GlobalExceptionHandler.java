package jpa.myunjuk.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<?> handleNoSuchDataException(final NoSuchDataException e){
        return ResponseEntity.badRequest().body(errorMsg(e.getNAME(), e.getMessage()));
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> handleDuplicateUserException(final DuplicateUserException e){
        return ResponseEntity.badRequest().body(errorMsg(e.getNAME(), e.getMessage()));
    }

    @ExceptionHandler(InvalidReqParamException.class)
    public ResponseEntity<?> handleInvalidReqParamException(final InvalidReqParamException e){
        return ResponseEntity.badRequest().body(errorMsg(e.getNAME(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    private Map<String, String> errorMsg(String name, String msg) {
        Map<String, String> error = new HashMap<>();
        error.put(name, msg);
        return error;
    }
}
