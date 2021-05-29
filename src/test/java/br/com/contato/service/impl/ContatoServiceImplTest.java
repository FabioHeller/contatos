package br.com.contato.service.impl;

import br.com.contato.entity.Contato;
import br.com.contato.exception.ContatoUnprocessableEntityException;
import br.com.contato.repository.ContatoRepository;
import br.com.contato.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceImplTest {

    @InjectMocks
    ContatoServiceImpl contatoService;

    @Mock
    ContatoRepository contatoRepository;

    private static final String FILE_CONTATO_VALIDO_JSON = "contato_valido.json";
    private Utils utils = new Utils();

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
    public void deveEditarContatoSucesso() throws IOException, URISyntaxException {
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
    public void deveBuscarContatoSucesso() throws IOException, URISyntaxException {
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

    private Contato getContatoTest() throws IOException, URISyntaxException {
        return this.utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
    }

}
