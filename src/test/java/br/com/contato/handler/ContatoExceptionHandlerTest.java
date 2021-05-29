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

import br.com.contato.exception.ContatoUnprocessableEntityException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContatoExceptionHandlerTest {

    @InjectMocks
    private ContatoExceptionHandler contatoExceptionHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void testContatoUnprocessableEntityExceptionHandler(){

        var contatoUnprocessableEntityException = new ContatoUnprocessableEntityException("001","teste");
        var result = contatoExceptionHandler.contatoUnprocessableEntityHandler(httpServletRequest, contatoUnprocessableEntityException);

        assertThat(result.getBody().getCode())
                .isEqualTo(contatoUnprocessableEntityException.getErrorCode());
        assertThat(result.getBody().getMessageError())
                .isEqualTo(contatoUnprocessableEntityException.getErrorMessage());
        assertThat(result.getStatusCode())
                .isEqualTo(contatoUnprocessableEntityException
                        .getClass()
                        .getAnnotation(ResponseStatus.class).value());
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

        var result = contatoExceptionHandler.methodArgumentNotValidHandler(httpServletRequest, methodArgumentNotValidException);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
