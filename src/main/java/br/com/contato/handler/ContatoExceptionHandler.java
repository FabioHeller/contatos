package br.com.contato.handler;

import br.com.contato.dto.ErrorFieldResponse;
import br.com.contato.dto.ErrorResponse;
import br.com.contato.exception.ContatoUnprocessableEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@ControllerAdvice
public class ContatoExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ContatoExceptionHandler.class);

    @ExceptionHandler(ContatoUnprocessableEntityException.class)
    public ResponseEntity<ErrorResponse> contatoUnprocessableEntityHandler(HttpServletRequest request, ContatoUnprocessableEntityException ex){
        var httpStatus = getHttpStatus(ex);
        var errorResponse = buildErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        log.info("#ContatoUnprocessableEntityException - ERROR ID INSISTENTE");
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFieldResponse> methodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException ex){
        var errorResponseField = buildDefaultValidateionError();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            var errorDetail = new ErrorFieldResponse.FieldError();
            errorDetail.setCampo(fieldError.getField());
            errorDetail.setMessage(fieldError.getDefaultMessage());
            errorResponseField.getFields().add(errorDetail);
        }
        log.info("#MethodArgumentNotValidException - ERROR CAMPOS INVALIDOS");
        return new ResponseEntity<>(errorResponseField, HttpStatus.BAD_REQUEST);
    }


    private HttpStatus getHttpStatus(Throwable ex){
        if(ex.getClass().isAnnotationPresent(ResponseStatus.class)){
            return ex.getClass().getAnnotation(ResponseStatus.class).value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ErrorResponse buildErrorResponse(String code, String message){
        var errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessageError(message);
        return errorResponse;
    }

    private ErrorFieldResponse buildDefaultValidateionError(){
        return buildErrorResponseField("VALIDATION_ERROR","Erro ao validar campos");
    }

    private ErrorFieldResponse buildErrorResponseField(String code, String message){
        var errorFieldResponse = new ErrorFieldResponse();
        errorFieldResponse.setCode(code);
        errorFieldResponse.setMessageError(message);
        errorFieldResponse.setFields(new ArrayList<>());
        return errorFieldResponse;
    }
}
