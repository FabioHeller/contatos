package br.com.contato.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.contato.exception.ContatoException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContatoHandlerTest {

    @InjectMocks
    private ContatoHandler contatoHandler;

    @Mock
    private HttpServletRequest httpServletRequest;


    public static final String ERROR_CODE_001 = "001";
    public static final String ID_INEXISTENTE_MESSAGE_ERROR = "teste message";

    @Test
    public void testContatoExceptionHandler(){

        var contatoException = new ContatoException(ERROR_CODE_001, ID_INEXISTENTE_MESSAGE_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        var result = contatoHandler.contatoUnprocessableEntityHandler(contatoException);

        assertThat(result.getBody().getCode())
                .isEqualTo(contatoException.getErrorCode());
        assertThat(result.getBody().getMessageError())
                .isEqualTo(contatoException.getErrorMessage());
        assertThat(result.getStatusCode())
                .isEqualTo(contatoException.getHttpStatus().value());
    }

    @Test
    public void testMethodArgumentNotValidExceptionHandler(){

        var fieldError = new FieldError("object","field","message");
        var methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(BindingResult.class);

        when(methodArgumentNotValidException.getBindingResult())
                .thenReturn(bindingResult);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(fieldError));

        var result = contatoHandler.methodArgumentNotValidHandler(httpServletRequest, methodArgumentNotValidException);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
