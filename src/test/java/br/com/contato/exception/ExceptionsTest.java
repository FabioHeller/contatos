package br.com.contato.exception;

import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
public class ExceptionsTest {

    @Test(expected = ContatoUnprocessableEntityException.class)
    public void ContatoUnprocessableEntityExceptionTest() throws ContatoUnprocessableEntityException {
        throw new ContatoUnprocessableEntityException(any(),any());
    }
}
