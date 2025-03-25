package tqs.hw1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import tqs.hw1.model.WeatherResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper(); 

    public WeatherResponse getForecast(String city, String date) throws Exception {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("unitGroup", "metric")  
                .queryParam("include", "hours,current,alerts,days") 
                .queryParam("key", apiKey)
                .queryParam("contentType", "json");

        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        return objectMapper.readValue(response.getBody(), WeatherResponse.class);
    }
}
