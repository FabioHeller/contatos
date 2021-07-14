package br.com.contato.resource;

import br.com.contato.entity.Contato;
import br.com.contato.service.ContatoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/contatos")
public class ContatoResource {

    @Autowired
    private ContatoService contatoService;

    @ApiOperation(value = "Inserir novo contato")
    @PostMapping( consumes = "application/json")
    public ResponseEntity<?> newContato ( @RequestBody @Valid Contato contato){
        return new ResponseEntity<Contato>(contatoService.newContato(contato), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar contato por ID")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateContatoById ( @RequestBody @Valid Contato contato, @NotEmpty @PathVariable ("id") @NotBlank String id){
        return ResponseEntity.ok().body(contatoService.updateContatoById(contato, id));
    }

    @ApiOperation(value = "Deletar contato por ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteContatoById (@NotEmpty @PathVariable("id") @NotBlank String id){
        contatoService.deleteContatoById(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Buscar contato por ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Contato> searchContatoById (@NotEmpty @PathVariable ("id") String id){
        return ResponseEntity.ok().body(contatoService.searchContatoById(id));
    }

    @ApiOperation(value = "Listar todos contatos")
    @GetMapping
    public ResponseEntity<List<Contato>> listContatos(){
        return ResponseEntity.ok().body(contatoService.listContato());
    }
}
