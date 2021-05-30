package br.com.contato.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoException extends RuntimeException {

    private static final long serialVersionUID = 361167219304015671L;

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
