package nl.novi.deepwrench42.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import nl.novi.deepwrench42.entities.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.postgresql.util.PSQLException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRecordNotFoundException(RecordNotFoundException ex) { return ex.getMessage();}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException exception && exception.getTargetType().isEnum()) {
            return ("Invalid enum value: " + exception.getValue());
        } else { return "Request not readable";}
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalStateException(IllegalStateException ex) {   return ex.getMessage(); }

    @ExceptionHandler(ForeignKeyViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleForeignKeyViolationException(ForeignKeyViolationException ex) { return ex.getMessage(); }

    @ExceptionHandler(DuplicateFieldException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDuplicateFieldException(DuplicateFieldException ex) {   return ex.getMessage(); }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
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
            if (msg.contains("inspections_tool_id_key")) {
                return ("Inspection for this tool ID already exists");
            }
            if (msg.contains("inspections_tool_kit_id_key")) {
                return ("Inspection for this tool kit ID already exists");
            }
            if (msg.contains("violates foreign key constraint")) {
                if (msg.contains("is not present")) {
                    return "Item not found / Invalid reference";
                }
                if (msg.contains("still referenced")) {
                    return "Unable to delete: item still in use";
                }
                return "Database: Foreign key violation";
            }
        }
        return "Data integrity violation exception";
    }

    @ExceptionHandler(ReadFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleReadFileException(ReadFileException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleFileStorageSystemException(IOException ex) {
        String message = "File Storage System Error";
        if (ex.getMessage() != null) {
            return ex.getMessage();
        } else {
            return message;
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ResponseBody
    public String handleFileSizeException(MaxUploadSizeExceededException ex) {
        String exception = ex.getMessage();
        return exception + ", info: max upload size = 10MB";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleServerErrorException(Exception ex) {
        return "There was an error on the server side";
    }
}

