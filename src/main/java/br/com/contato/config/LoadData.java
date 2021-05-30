package br.com.contato.config;

import br.com.contato.controller.ContatoController;
import br.com.contato.entity.Contato;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {
    private static final Logger log = LoggerFactory.getLogger(LoadData.class);
    @Bean
    CommandLineRunner initDataBase (ContatoController contatoController){
        return args -> {
            log.info("\n#LoadData - Inserido contato: " + contatoController.newContato(new Contato("1","Pedro Santos","31 98765-1232","pedro@teste.com.br")));
            log.info("\n#LoadData - Inserido contato: " + contatoController.newContato(new Contato("2","Matias Ferreira","31 98765-1232","matias@teste.com.br")));
            log.info("\n#LoadData - Inserido contato: " + contatoController.newContato(new Contato("3","Fernanda Melo","31 98765-1232","fernanda@teste.com.br")));
        };
    }
}
