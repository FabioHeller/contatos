package br.com.contato.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="CONTATOS")
@Data
public class Contato implements Serializable {

    private static final long serialVersionUID = 5357712845564399596L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=5, max=80, message="Para nome informe o minímo de 5 e maximmo de 80 caracteres.")
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="contato_id")
    private List<Telefone> telefoneList;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
            +"@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="{Email inválido}")
    @Size(max=80, message="Para email informe o maximmo de 80 caracteres.")
    private String email;
}
