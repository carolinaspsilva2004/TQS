package tqs.hw1.controller;

import tqs.hw1.service.WeatherService;
import tqs.hw1.model.WeatherResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}/{date}")
    public WeatherResponse getWeatherForDate(@PathVariable String city, @PathVariable String date) throws Exception {
        // Obtém a previsão do tempo para a cidade e a data
        return weatherService.getForecast(city, date);
    }

    @GetMapping("/{city}/{date}/current")
    public WeatherResponse getDaysAndCurrentWeather(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        // Retorna apenas os dados de "current"
        return new WeatherResponse(
            null,
            null, 
            weatherResponse.getCurrentConditions(),
            null, 
            null
        );
    }

    @GetMapping("/{city}/{date}/alerts")
    public WeatherResponse getWeatherAlerts(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        // Retorna apenas os dados de "alerts"
        return new WeatherResponse(
            null, 
            null, 
            null, 
            weatherResponse.getAlerts(), 
            null
        );
    }

    @GetMapping("/{city}/{date}/events")
    public WeatherResponse getWeatherEvents(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        // Retorna apenas os dados de "events"
        return new WeatherResponse(
            null, 
            weatherResponse.getEvents(), 
            null, 
            null, 
            null
        );
    }

    @GetMapping("/{city}/{date}/hours")
    public WeatherResponse getWeatherHours(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        // Retorna apenas os dados de "hours"
        return new WeatherResponse(
            null, 
            null, 
            null, 
            null, 
            weatherResponse.getHours()
        );
    }
}
