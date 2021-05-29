package br.com.contato.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorFieldResponse {
    @JsonProperty("code_erro")
    private String code;

    @JsonProperty("message_erro")
    private String messageError;

    @JsonProperty("campos")
    private List<FieldError> fields;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError{

        @JsonProperty("campo")
        private String campo;

        @JsonProperty("mensagem")
        private String message;
    }

}
