package br.com.contato.controller;

import br.com.contato.entity.Contato;
import br.com.contato.entity.Telefone;
import br.com.contato.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    ContatoRepository contatoRepository;

    @Test
    public void deveRetornarInserirNovoContatoSucesso () throws Exception {
        String json = "{\n" +
                "    \"nome\":\"Pedro\",\n" +
                "    \"telefoneList\":[\n" +
                "    {\"telefone\":\"3198765-1111\"}\n" +
                "    ],\n" +
                "    \"email\":\"teste@teste.com.br\"\n" +
                "}";
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/contatos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void deveRetornarExcecaoAoSalvarContatoSemNome () throws Exception {
        String json = "{\n" +
                "    \"nome\":\"\",\n" +
                "    \"telefoneList\":[\n" +
                "    {\"telefone\":\"3198765-1111\"}\n" +
                "    ],\n" +
                "    \"email\":\"teste@teste.com.br\"\n" +
                "}";
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/contatos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornarExcecaoAoAtualizarPorCampoNaoInformado () throws Exception {
        when(contatoRepository.findById(getContatoTest().getId())).thenReturn(Optional.of(getContatoTest()));
        String json = "{\n" +
                "    \"nome\":\"\",\n" +
                "    \"telefoneList\":[\n" +
                "    {\"telefone\":\"3198765-1111\"}\n" +
                "    ],\n" +
                "    \"email\":\"teste@teste.com.br\"\n" +
                "}";
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/contatos/atualizar_por_id")
                        .content(json)
                        .queryParam("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
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
