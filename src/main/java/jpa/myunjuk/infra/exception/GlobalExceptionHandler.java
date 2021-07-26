package jpa.myunjuk.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            DuplicateUserException.class,
            NoSuchDataException.class,
            InvalidReqParamException.class})
    public ResponseEntity<?> handleRuntimeExceptions(final CustomRuntimeException e) {
        return ResponseEntity.badRequest().body(errorMsg(e.getName(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleReqParamExceptions(MissingServletRequestParameterException e){
        return ResponseEntity.badRequest().body(errorMsg(e.getParameterName(), e.getMessage()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseExceptions(DateTimeParseException e){
        return ResponseEntity.badRequest().body(errorMsg(e.getParsedString(), e.getMessage()));
    }

    private Map<String, String> errorMsg(String name, String msg) {
        Map<String, String> error = new HashMap<>();
        error.put(name, msg);
        return error;
    }
}
