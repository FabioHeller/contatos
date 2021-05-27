package br.com.contato.exception;

public class ContatoUnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = 361167219304015671L;

    private String errorCode;
    private String errorMessage;

    public ContatoUnprocessableEntityException(Throwable throwable){ super(throwable); }

    public ContatoUnprocessableEntityException(String msg){ super(msg); }

    public ContatoUnprocessableEntityException(String msg, Throwable throwable){ super(msg, throwable); }

    public ContatoUnprocessableEntityException(String errorCode, String errorMessage){
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


}
