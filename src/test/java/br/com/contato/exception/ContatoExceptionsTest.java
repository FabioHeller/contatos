package br.com.contato.exception;

import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
public class ContatoExceptionsTest {

    @Test(expected = ContatoException.class)
    public void ContatoExceptionTest() throws ContatoException {
        throw new ContatoException();
    }
}
