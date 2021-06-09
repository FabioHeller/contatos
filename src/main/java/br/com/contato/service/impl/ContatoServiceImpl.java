package br.com.contato.service.impl;

import br.com.contato.entity.Contato;
import br.com.contato.exception.ContatoException;
import br.com.contato.service.ContatoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContatoServiceImpl implements ContatoService {

    public static final String ERROR_CODE_002 = "002";
    public static final String ERROR_CODE_001 = "001";
    public static final String ID_EXISTENTE_MENSSAGEM_ERROR = "Id existente";
    public static final String ID_INEXISTENTE_MENSSAGEM_ERROR = "Id inexistente";

    @Override
    public Contato newContato(List<Contato> contatoList, Contato contato) {
        contatoList
                .stream()
                .filter(e -> e.getId().equals(contato.getId()))
                .findAny()
                .ifPresentOrElse(
                        item -> { throw new ContatoException(ERROR_CODE_001, ID_EXISTENTE_MENSSAGEM_ERROR, HttpStatus.BAD_REQUEST); },
                        () ->{ contatoList.add(contato); });
        return contato;
    }

    @Override
    public Contato updateContatoById(List<Contato> contatoList, Contato contato, String id) {
        contatoList
                .stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .ifPresentOrElse(
                        item -> {
                            contato.setId(item.getId());
                            item.setEmail(contato.getEmail());
                            item.setNome(contato.getNome());
                            item.setTelefone(contato.getTelefone());
                        },
                        () ->{ throw new ContatoException(ERROR_CODE_002, ID_INEXISTENTE_MENSSAGEM_ERROR, HttpStatus.UNPROCESSABLE_ENTITY); });
        return contato;
    }

    @Override
    public Contato searchContatoById(List<Contato> contatoList, String id) {
        Optional<Contato> seachCotanto = contatoList
                .stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
        if(!seachCotanto.isPresent()) {
            throw new ContatoException(ERROR_CODE_002, ID_INEXISTENTE_MENSSAGEM_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return seachCotanto.get();
    }

    @Override
    public void deleteContatoById(List<Contato> contatoList, String id) {
        Optional<Contato> seachCotanto = contatoList
                .stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
        if(!seachCotanto.isPresent()) {
            throw new ContatoException(ERROR_CODE_002, ID_INEXISTENTE_MENSSAGEM_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            contatoList.remove(seachCotanto.get());
        }
    }

    @Override
    public List<Contato> listContato(List<Contato> contatoList) {
        return contatoList.stream().collect(Collectors.toList());
    }
}
