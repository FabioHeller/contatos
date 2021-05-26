package br.com.contato.config;

import br.com.contato.entity.Contato;
import br.com.contato.repository.ContatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {
    private static final Logger log = LoggerFactory.getLogger(LoadData.class);
    @Bean
    CommandLineRunner initDataBase (ContatoRepository contatoRepository){
        return args -> {
            log.info("\n#LoadData - Inserido contato: " + contatoRepository.save(new Contato(1L,"Fabio Santos","31 98765-1232","fabio@teste.com.br")));
            log.info("\n#LoadData - Inserido contato: " + contatoRepository.save(new Contato(2L,"Pedro Silva","31 97761-5333","pedro@teste.com.br")));
            log.info("\n#LoadData - Inserido contato: " + contatoRepository.save(new Contato(3L,"Marcos ferreira","31 96765-3411","marcos@teste.com.br")));
        };
    }
}
