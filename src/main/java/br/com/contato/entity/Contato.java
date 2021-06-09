package br.com.contato.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
public class Contato implements Serializable {

    private static final long serialVersionUID = 5357712845564399596L;

    @Id
    @NotBlank(message = "Id não pode ser vazio")
    private String id;

    @Size(min=5, max=80, message="Para nome informe o minímo de 5 e maximmo de 80 caracteres.")
    private String nome;

    private String telefone;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
            +"@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="{Email inválido}")
    @Size(max=80, message="Para email informe o maximmo de 80 caracteres.")
    private String email;
}
