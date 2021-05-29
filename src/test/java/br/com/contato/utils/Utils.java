package br.com.contato.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@NoArgsConstructor
public class Utils {

    private static final String BASE_PATH = "/files/";

    ObjectMapper mapper;

    public <T> T buildObjectByJson(String pathFileJson, Class<T> clazz) throws URISyntaxException, IOException {
        String path = BASE_PATH + pathFileJson;
        URI uri = getClass().getResource(path).toURI();
        File fileJson = new File(uri);
        this.mapper = Optional.ofNullable(this.mapper).isPresent()?this.mapper:new ObjectMapper();
        return this.mapper.readValue(fileJson, clazz);
    }

    public String toJsonString (Object json) throws JsonProcessingException {
        this.mapper = Optional.ofNullable(this.mapper).isPresent()?this.mapper:new ObjectMapper();
        return this.mapper.writeValueAsString(json);
    }
}
