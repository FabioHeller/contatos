package br.com.contato.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ContatoUnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = 361167219304015671L;

    private String errorCode;
    private String errorMessage;

    public ContatoUnprocessableEntityException(String errorCode, String errorMessage){
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
