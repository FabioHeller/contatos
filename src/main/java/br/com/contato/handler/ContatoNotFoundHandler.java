package br.com.contato.handler;

import br.com.contato.dto.ErrorResponse;
import br.com.contato.exception.ContatoUnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ContatoNotFoundHandler {
    private ErrorResponse errorResponse = new ErrorResponse();

    @ResponseBody
    @ExceptionHandler(ContatoUnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Object ContatoUnprocessableEntityHandler(ContatoUnprocessableEntityException contatoUnprocessableEntityException){
        errorResponse.setCode("001");
        errorResponse.setMessageError(contatoUnprocessableEntityException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
