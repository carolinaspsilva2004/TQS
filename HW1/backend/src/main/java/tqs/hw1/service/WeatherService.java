package tqs.hw1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;  // Defina a chave da API no arquivo application.properties
    
    private final RestTemplate restTemplate = new RestTemplate();

    public String getForecast(String city, String date) {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city;

        // Construa a URL para fazer a requisição à API com os parâmetros necessários
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "events,days,alerts,current,hours")
                .queryParam("key", apiKey)
                .queryParam("contentType", "json");

        // Realiza a requisição HTTP para obter a previsão
        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        // Retorna a resposta da API externa
        return response.getBody();
    }
}
