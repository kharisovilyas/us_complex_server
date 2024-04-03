package ru.spiiran.us_complex.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;

@Service
public class FlightModelService {
    @Value("${smao.api.host}") // Путь к директории для загружаемых изображений
    private String smaoHost;
    @Value("${smao.api.directory}") // Путь к директории для загружаемых изображений
    private String smaoDirectory;
    @Value("${smao.api.port}") // Путь к директории для загружаемых изображений
    private String smaoPort;

    @Value("${smao.api.url.modelling.start}") // Путь к директории для загружаемых изображений
    private String smaoStartModellingURL;
    public dtoMessage startModelling() {
        final String URL = "http://" + smaoHost + ":" + smaoPort + smaoStartModellingURL;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"host\": \"" + smaoHost + "\", \"directory\": \"" + smaoDirectory + "\", \"port\": " + smaoPort + "}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
        return new dtoMessage("Server started successfully.", responseEntity.getBody());
    }

    public ResponseEntity<dtoMessage> pauseModelling(){

        return null;
    }
}
