package pe.edu.upc.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(value= HttpStatus.CONFLICT)
    public ErrorMessage invalidActionException(InvalidActionException exception, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                exception.getClass().getName(),
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage invalidDataException(InvalidDataException exception, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                exception.getClass().getName(),
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }


    @ExceptionHandler(RequiredDataException.class)
    @ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage requiredDataException(RequiredDataException exception, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                exception.getClass().getName(),
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }

    @ExceptionHandler(KeyRepeatedDataException.class)
    @ResponseStatus(value= HttpStatus.CONFLICT)
    public ErrorMessage keyRepeatedDataException(KeyRepeatedDataException exception, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                exception.getClass().getName(),
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorMessage errorMessage= new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                exception.getClass().getName(),
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }




}



