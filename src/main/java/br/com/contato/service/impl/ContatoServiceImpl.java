package br.com.contato.service.impl;

import br.com.contato.entity.Contato;
import br.com.contato.exception.ContatoException;
import br.com.contato.repository.ContatoRepository;
import br.com.contato.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContatoServiceImpl implements ContatoService {

    public static final String ERROR_CODE_001 = "001";
    public static final String ID_INEXISTENTE_MESSAGE_ERROR = "Id inexistente";

    @Autowired
    private ContatoRepository contatoRepository;

    @Override
    public Contato newContato(Contato contato) {
        return contatoRepository.save(contato);
    }

    @Override
    public Optional<Contato> updateContatoById(Contato contato, Long id) {
        return contatoRepository
                .findById(id)
                .map(updateContato -> {
                    updateContato.setNome(contato.getNome());
                    updateContato.setTelefoneList(contato.getTelefoneList().stream().collect(Collectors.toList()));
                    updateContato.setEmail(contato.getEmail());
                    contatoRepository.save(updateContato);
                    return contatoRepository.findById(id);
                }).orElseThrow(()-> new ContatoException(ERROR_CODE_001, ID_INEXISTENTE_MESSAGE_ERROR, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public Contato searchContatoById(Long id) {
        return contatoRepository.findById(id).orElseThrow(()->new ContatoException(ERROR_CODE_001, ID_INEXISTENTE_MESSAGE_ERROR, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public List<Contato> listContato() {
        List<Contato> listContatos = new ArrayList<>();
        contatoRepository.findAll().forEach(listContatos::add);
        return listContatos;
    }

    @Override
    public void deleteContatoById(Long id) {
        contatoRepository.findById(id).orElseThrow(()-> new ContatoException(ERROR_CODE_001, ID_INEXISTENTE_MESSAGE_ERROR, HttpStatus.UNPROCESSABLE_ENTITY));
        contatoRepository.deleteById(id);
    }
}
