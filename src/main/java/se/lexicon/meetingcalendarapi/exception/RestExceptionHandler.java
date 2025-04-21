package se.lexicon.meetingcalendarapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DataNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomException(Exception ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;;
        ErrorResponseDto errorResponse= new ErrorResponseDto(status, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }


}
