package br.com.contato.handler;

import br.com.contato.dto.ErrorMessage;
import br.com.contato.exception.ContatoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ContatoHandler {

    private ErrorMessage errorMessage = new ErrorMessage();

    @ResponseBody
    @ExceptionHandler(ContatoException.class)
    public Object contatoNotFoundHandler(ContatoException contatoException){
        errorMessage.setCode(contatoException.getErrorCode());
        errorMessage.setMessageError(contatoException.getErrorMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, contatoException.getHttpStatus());
    }
}
