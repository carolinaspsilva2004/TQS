package tqs.hw1.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import tqs.hw1.model.WeatherResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final MeterRegistry meterRegistry;

    // Contadores para monitorizar cache
    private final AtomicInteger totalRequests = new AtomicInteger(0);
    private final AtomicInteger cacheHits = new AtomicInteger(0);
    private final AtomicInteger cacheMisses = new AtomicInteger(0);

    private final Cache<String, WeatherResponse> cache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.DAYS)
            .maximumSize(1000) // Define um limite para evitar crescimento descontrolado
            .recordStats()
            .build();

    // Injeção do Micrometer para métricas
    @Autowired
    public WeatherService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meterRegistry.gauge("weather_cache_total_requests", totalRequests);
        meterRegistry.gauge("weather_cache_hits", cacheHits);
        meterRegistry.gauge("weather_cache_misses", cacheMisses);
    }

    public WeatherResponse getForecast(String city, String date) throws Exception {
        String cacheKey = city + "-" + date;
        totalRequests.incrementAndGet();

        // Tenta buscar os dados do cache
        WeatherResponse cachedResponse = cache.getIfPresent(cacheKey);
        if (cachedResponse != null) {
            cacheHits.incrementAndGet();
            return cachedResponse;
        }

        // Caso não encontre, faz a requisição externa e guarda no cache
        cacheMisses.incrementAndGet();
        WeatherResponse weatherResponse = fetchWeather(city, date);
        cache.put(cacheKey, weatherResponse);
        return weatherResponse;
    }

    private WeatherResponse fetchWeather(String city, String date) throws Exception {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("unitGroup", "metric")
                .queryParam("include", "hours,current,alerts,days")
                .queryParam("key", apiKey)  // Usa a apiKey injetada
                .queryParam("contentType", "json");

        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);
        return objectMapper.readValue(response.getBody(), WeatherResponse.class);
    }

    public int getTotalRequests() { return totalRequests.get(); }
    public int getCacheHits() { return cacheHits.get(); }
    public int getCacheMisses() { return cacheMisses.get(); }
}
