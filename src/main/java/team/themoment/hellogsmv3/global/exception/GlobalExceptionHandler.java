package team.themoment.hellogsmv3.global.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;
import team.themoment.hellogsmv3.global.exception.model.ExceptionResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpectedException.class)
    private ResponseEntity<ExceptionResponseEntity> expectedException(ExpectedException ex) {
        log.warn("ExpectedException : {} ", ex.getMessage());
        log.trace("ExpectedException Details : ", ex);
        return ResponseEntity.status(ex.getStatusCode().value())
                .body(ExceptionResponseEntity.of(ex));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponseEntity> validationException(MethodArgumentNotValidException ex) {
        log.warn("Validation Failed : {}", ex.getMessage());
        log.trace("Validation Failed Details : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ExceptionResponseEntity(methodArgumentNotValidExceptionToJson(ex)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseEntity> validationException(ConstraintViolationException ex) {
        log.warn("field validation failed : {}", ex.getMessage());
        log.trace("field validation failed : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ExceptionResponseEntity("field validation failed : " + ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseEntity> unExpectedException(RuntimeException ex) {
        log.error("UnExpectedException Occur : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ExceptionResponseEntity("internal server error has occurred"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponseEntity> noHandlerFoundException(NoHandlerFoundException ex) {
        log.warn("Not Found Endpoint : {}", ex.getMessage());
        log.trace("Not Found Endpoint Details : ", ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ExceptionResponseEntity(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponseEntity> maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.warn("The file is too big : {}", ex.getMessage());
        log.trace("The file is too big Details : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ExceptionResponseEntity("The file is too big, limited file size : " + ex.getMaxUploadSize()));
    }

    private static String methodArgumentNotValidExceptionToJson(MethodArgumentNotValidException ex) {
        Map<String, Object> globalResults = new HashMap<>();
        Map<String, String> fieldResults = new HashMap<>();

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            globalResults.put(ex.getBindingResult().getObjectName(), error.getDefaultMessage());
        });
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldResults.put(error.getField(), error.getDefaultMessage());
        });
        globalResults.put(ex.getBindingResult().getObjectName(), fieldResults);

        return new JSONObject(globalResults).toString().replace("\"", "'");
    }

}