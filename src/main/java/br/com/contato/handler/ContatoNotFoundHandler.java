package br.com.contato.handler;

import br.com.contato.dto.ErrorResponse;
import br.com.contato.exception.ContatoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ContatoNotFoundHandler {
    private ErrorResponse errorResponse = new ErrorResponse();

    @ResponseBody
    @ExceptionHandler(ContatoNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Object contatoNotFoundHandler(ContatoNotFoundException contatoNotFound){
        errorResponse.setCode("001");
        errorResponse.setMessageError(contatoNotFound.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
