package br.com.contato.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="TELEFONES")
@Data
public class Telefone  implements Serializable {

    private static final long serialVersionUID = -2351556715171360394L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long contato_id;

    private String telefone;

}



