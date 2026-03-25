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
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleException(RecordNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception ex) {
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
            if (msg.contains("inspections_tool_id_key")) {
                return ("Inspection for this tool ID already exists");
            }
            if (msg.contains("inspections_tool_kit_id_key")) {
                return ("Inspection for this tool kit ID already exists");
            }
            if (msg.contains("violates foreign key constraint")) {
                String detail = parseDetails(msg);
                if (msg.contains("is not present")) {
                    return "Invalid reference: " + detail;
                } else if (msg.contains("still referenced")) {
                    return "Unable to delete: item still in use (" + detail + ")";
                }
                return "Foreign key violation: " + detail;
            }
            return ("Data integrity violation");
        }
        return "Data integrity violation";
    }

    private String parseDetails(String msg) {
        var matcher = java.util.regex.Pattern.compile("Key \\(([^)]+)\\)=\\((\\d+)\\)").matcher(msg);
        if (matcher.find()) {
            String field = matcher.group(1);
            String id = matcher.group(2);
            return field + "=" + id;
        }
        return "unknown reference";
    }

    @ExceptionHandler(DuplicateFieldException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicate(DuplicateFieldException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidEnum(IllegalArgumentException ex) {
        if (ex.getMessage().contains("No enum constant")) {
            String enumSimpleName = extractEnumSimpleName(ex.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid enum value");

            String[] validValues = getValidEnumValues(enumSimpleName);
            response.put(enumSimpleName, validValues);

            return response;
        }
        return Map.of("message", ex.getMessage());
    }

    private String[] getValidEnumValues(String enumName) {
        return switch (enumName) {
            case "EquipmentStatus" -> Arrays.stream(EquipmentStatus.values()).map(Enum::name).toArray(String[]::new);
            case "EquipmentType" -> Arrays.stream(EquipmentType.values()).map(Enum::name).toArray(String[]::new);
            case "InspectionType" -> Arrays.stream(InspectionType.values()).map(Enum::name).toArray(String[]::new);
            case "ToolLogActionType" -> Arrays.stream(ToolLogActionType.values()).map(Enum::name).toArray(String[]::new);
            case "UserRole" -> Arrays.stream(UserRole.values()).map(Enum::name).toArray(String[]::new);
            default -> new String[0];
        };
    }

    private String extractEnumSimpleName(String errorMsg) {
        int lastDot = errorMsg.lastIndexOf('.');
        int secondLastDot = errorMsg.substring(0, lastDot).lastIndexOf('.');
        return errorMsg.substring(secondLastDot + 1, lastDot);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidJson(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidEx && invalidEx.getTargetType().isEnum()) {
            String enumName = invalidEx.getTargetType().getSimpleName();
            String invalidValue = invalidEx.getValue().toString();
            response.put("message", "Invalid " + enumName + ": '" + invalidValue + "'");
            response.put(enumName, getValidEnumValues(enumName));
            return response;
        }

        response.put("message", "Invalid JSON syntax");
        response.put("error", ex.getMessage());
        return response;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalState(IllegalStateException ex) {
        return Map.of(
                "error", ex.getMessage()
        );
    }

    @ExceptionHandler(ReadFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(ReadFileException ex) {
            return ex.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(IOException ex) {
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
    public String handleException(MaxUploadSizeExceededException ex) {
        String exception = ex.getMessage();
        return exception + ", info: max upload size = 10MB";
    }
}

