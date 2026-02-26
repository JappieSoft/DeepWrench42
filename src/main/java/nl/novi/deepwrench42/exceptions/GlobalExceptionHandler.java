package nl.novi.deepwrench42.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.postgresql.util.PSQLException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleException(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleException(RecordNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception ex){
        return "There was an error on the server side";
    }

    @ExceptionHandler(ForeignKeyViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleForeignKeyViolation(ForeignKeyViolationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataIntegrity(DataIntegrityViolationException ex) {
        var root = ex.getRootCause();
        if (root instanceof PSQLException psql) {
            var msg = psql.getMessage();
            if (msg.contains("users_employee_id_key")) {
                return ("Employee ID already exists");
            }
            if (msg.contains("users_schiphol_id_key")) {
                return ("Schiphol ID already exists");
            }
            if (msg.contains("users_email_key")) {
                return ("Email already exists");
            }
            if (msg.contains("violates foreign key constraint")) {
                return ("Unable to delete: requested ID is still in use");
            }
        }
        return ("Data integrity violation");
    }

    @ExceptionHandler(DuplicateFieldException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicate(DuplicateFieldException ex) {
        return ex.getMessage();
    }
}