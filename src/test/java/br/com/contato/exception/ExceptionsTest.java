package br.com.contato.exception;

import org.junit.Test;

public class ExceptionsTest {

    @Test(expected = ContatoUnprocessableEntityException.class)
    public void ContatoUnprocessableEntityExceptionTest() throws ContatoUnprocessableEntityException {
        throw new ContatoUnprocessableEntityException("001","Error");
    }
}
