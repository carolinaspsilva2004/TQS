package tqs.hw1.controller;

import tqs.hw1.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}/{date}")
    public String getWeatherForDate(@PathVariable String city, @PathVariable String date) {
        // Obtém a previsão do tempo para a cidade e a data
        return weatherService.getForecast(city, date);
    }
}
