package tqs.hw1.controller;

import tqs.hw1.service.WeatherService;
import tqs.hw1.model.WeatherResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}/{date}")
    public WeatherResponse getWeatherForDate(@PathVariable String city, @PathVariable String date) throws Exception {
        return weatherService.getForecast(city, date);
    }

    @GetMapping("/{city}/{date}/current")
    public WeatherResponse getDaysAndCurrentWeather(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        return new WeatherResponse(
            null,
            weatherResponse.getCurrentConditions(),
            null
        );
    }

    @GetMapping("/{city}/{date}/alerts")
    public WeatherResponse getWeatherAlerts(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
        
        return new WeatherResponse(
            null, 
            null, 
            weatherResponse.getAlerts()
        );
    }

    @GetMapping("/{city}/{date}/hours")
    public List<WeatherResponse.Day.Hours> getWeatherHours(@PathVariable String city, @PathVariable String date) throws Exception {
        WeatherResponse weatherResponse = weatherService.getForecast(city, date);
    
        WeatherResponse.Day selectedDay = weatherResponse.getDays().stream()
            .filter(day -> day.getDatetime().equals(date))
            .findFirst()
            .orElseThrow(() -> new Exception("Date not found in response"));
    
        return selectedDay.getHours();
    }
    
}
