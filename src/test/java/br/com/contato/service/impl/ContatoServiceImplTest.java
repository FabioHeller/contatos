package br.com.contato.service.impl;

import br.com.contato.entity.Contato;
import br.com.contato.entity.Telefone;
import br.com.contato.exception.ContatoUnprocessableEntityException;
import br.com.contato.repository.ContatoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceImplTest {
    @InjectMocks
    ContatoServiceImpl contatoService;

    @Mock
    ContatoRepository contatoRepository;

    @Test
    public void deveRetornarExcecaoAoBuscarPorIdInexistente(){
        assertThrows(ContatoUnprocessableEntityException.class, ()
                -> contatoService.searchContatoById(anyLong()));
    }

    @Test
    public void deveRetornarExcecaoAoDeletarPorIdInexistente(){
        assertThrows(ContatoUnprocessableEntityException.class, ()
                -> contatoService.deleteContatoById(anyLong()));
    }

    @Test
    public void deveRetornarExcecaoAoEditarPorIdInexistente(){
        assertThrows(ContatoUnprocessableEntityException.class, ()
                -> contatoService.updateContatoById(mock(Contato.class),anyLong()));
    }

    @Test
    public void deveEditarContatoSucesso(){
        Contato contatoTest = getContatoTest();
        when(contatoRepository.findById(contatoTest.getId())).thenReturn(Optional.of(contatoTest));
        contatoTest.setNome("Novo nome");
        var contatoExistente = contatoService.updateContatoById(contatoTest, contatoTest.getId());

        assertEquals(contatoExistente.get().getId(), contatoTest.getId());
        assertEquals(contatoExistente.get().getNome(), contatoTest.getNome());
        assertEquals(contatoExistente.get().getEmail(), contatoTest.getEmail());
        assertEquals(contatoExistente.get().getTelefoneList().stream().findFirst().get().getId(),
                contatoTest.getTelefoneList().stream().findFirst().get().getId());
        assertEquals(contatoExistente.get().getTelefoneList().stream().findFirst().get().getContato_id(),
                contatoTest.getTelefoneList().stream().findFirst().get().getContato_id());
        assertEquals(contatoExistente.get().getTelefoneList().stream().findFirst().get().getTelefone(),
                contatoTest.getTelefoneList().stream().findFirst().get().getTelefone());
    }

    @Test
    public void deveBuscarContatoSucesso(){
        Contato contatoTest = getContatoTest();
        when(contatoRepository.findById(contatoTest.getId())).thenReturn(Optional.of(contatoTest));
        var contatoExistente = contatoService.searchContatoById(contatoTest.getId());

        assertEquals(contatoExistente.getId(), contatoTest.getId());
        assertEquals(contatoExistente.getNome(), contatoTest.getNome());
        assertEquals(contatoExistente.getEmail(), contatoTest.getEmail());
        assertEquals(contatoExistente.getTelefoneList().stream().findFirst().get().getId(),
                contatoTest.getTelefoneList().stream().findFirst().get().getId());
        assertEquals(contatoExistente.getTelefoneList().stream().findFirst().get().getContato_id(),
                contatoTest.getTelefoneList().stream().findFirst().get().getContato_id());
        assertEquals(contatoExistente.getTelefoneList().stream().findFirst().get().getTelefone(),
                contatoTest.getTelefoneList().stream().findFirst().get().getTelefone());
    }

    private Contato getContatoTest(){
        Contato contato = new Contato();
        Telefone telefone = new Telefone();
        List<Telefone> listTelefone = new ArrayList<Telefone>();
        contato.setId(1L);
        contato.setNome("Nome");
        contato.setEmail("teste@teste.com");
        telefone.setId(1L);
        telefone.setContato_id(contato.getId());
        telefone.setTelefone("12341234");
        listTelefone.add(telefone);
        contato.setTelefoneList(listTelefone.stream().collect(Collectors.toList()));
        return contato;
    }
}
