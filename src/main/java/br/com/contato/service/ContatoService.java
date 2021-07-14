package br.com.contato.service;

import br.com.contato.entity.Contato;

import java.util.List;
import java.util.Optional;

public interface ContatoService {
    Contato newContato (Contato contato);
    Optional<Contato> updateContatoById (Contato contato, String idContato);
    Contato searchContatoById (String idContato);
    List<Contato> listContato();
    void deleteContatoById(String idContato);
}
