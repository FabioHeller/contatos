package br.com.contato.controller;

import br.com.contato.entity.Contato;
import br.com.contato.repository.ContatoRepository;
import br.com.contato.service.impl.ContatoServiceImpl;
import br.com.contato.utils.Utils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoControllerTest {

    public static final String API_V2_CONTATOS = "/api/v2/contatos";
    public static final String API_V2_CONTATOS_DELETAR_POR_ID = "/api/v2/contatos/deletar_por_id";
    public static final String API_V2_CONTATOS_BUSCAR_POR_ID = "/api/v2/contatos/buscar_por_id";
    public static final String API_V2_CONTATOS_ATUALIZAR_POR_ID = "/api/v2/contatos/atualizar_por_id";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ContatoServiceImpl contatoService;

    @Mock
    ContatoRepository contatoRepository;

    private static final String FILE_CONTATO_VALIDO_JSON = "contato_valido.json";
    private Utils utils = new Utils();

    @Test
    public void deveRetornarSucessoNovoContato () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contato.setId(null);
        String json = utils.toJsonString(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post(API_V2_CONTATOS)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void deveRetornarSucessoDeletarContato () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contatoService.newContato(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_V2_CONTATOS_DELETAR_POR_ID)
                        .queryParam("id", String.valueOf(contato.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornarSucessoBuscarContato () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contatoService.newContato(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_V2_CONTATOS_BUSCAR_POR_ID)
                        .queryParam("id", contato.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornarSucessoAtualizarContato () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contatoService.newContato(contato);
        contato.setNome("Matias Ferreira");
        contato.setEmail("novoemail@email.com");
        when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
        String json = utils.toJsonString(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(API_V2_CONTATOS_ATUALIZAR_POR_ID)
                        .content(json)
                        .queryParam("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornarExcecaoNovoContatoSemNome () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contato.setId(null);
        contato.setNome(null);
        String json = utils.toJsonString(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post(API_V2_CONTATOS)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornarExcecaoAtualizarContatoSemNome () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        contato.setNome(null);
        when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
        String json = utils.toJsonString(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(API_V2_CONTATOS_ATUALIZAR_POR_ID)
                        .content(json)
                        .queryParam("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornarExcecaoAtualizarContatoQueryParamInesistente () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
        String json = utils.toJsonString(contato);
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(API_V2_CONTATOS_ATUALIZAR_POR_ID)
                        .content(json)
                        .queryParam("id", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deveRetornarExcecaoBuscarContatoQueryParamInesistente () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_V2_CONTATOS_BUSCAR_POR_ID)
                        .queryParam("id", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deveRetornarExcecaoDeletarContatoQueryParamInesistente () throws Exception {
        Contato contato = utils.buildObjectByJson(FILE_CONTATO_VALIDO_JSON, Contato.class);
        when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_V2_CONTATOS_DELETAR_POR_ID)
                        .queryParam("id", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
