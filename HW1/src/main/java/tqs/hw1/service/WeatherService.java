package tqs.hw1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.time.*;

@Service
public class WeatherService {
    private final Map<LocalDate, String> weatherCache = new HashMap<>();
    private final Map<LocalDate, LocalDateTime> cacheExpiry = new HashMap<>();

    public String getForecast(LocalDate date) {
        if (weatherCache.containsKey(date) &&
                cacheExpiry.get(date).isAfter(LocalDateTime.now())) {
            return weatherCache.get(date);
        }
        // Aqui você poderá substituir pela API real
        String forecast = "Sunny";
        weatherCache.put(date, forecast);
        cacheExpiry.put(date, LocalDateTime.now().plusHours(6));
        return forecast;
    }
}
