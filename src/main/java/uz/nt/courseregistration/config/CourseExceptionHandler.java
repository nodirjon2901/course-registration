package uz.nt.courseregistration.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.nt.courseregistration.domain.exception.AlreadyExistsException;
import uz.nt.courseregistration.domain.exception.DataNotFoundException;
import uz.nt.courseregistration.domain.exception.WrongCredentialsException;

@ControllerAdvice
public class CourseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<String> hanleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(402).body(e.getMessage());
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<String> hanleDataNotFoundException(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = WrongCredentialsException.class)
    public ResponseEntity<String> hanleWrongCredentialsException(WrongCredentialsException e) {
        return ResponseEntity.status(405).body(e.getMessage());
    }


}
