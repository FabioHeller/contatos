package br.com.contato.service;

import br.com.contato.entity.Contato;

import java.util.List;

public interface ContatoService {

    Contato newContato (List<Contato> contatoList, Contato contato);

    Contato updateContatoById (List<Contato> contatoList, Contato contato, String id);

    Contato searchContatoById (List<Contato> contatoList, String id);

    void deleteContatoById(List<Contato> contatoList, String id);

    List<Contato> listContato(List<Contato> contatoList);
}
