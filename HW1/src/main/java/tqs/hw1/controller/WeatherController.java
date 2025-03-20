package tqs.hw1.controller;

import tqs.hw1.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/{date}")
    public String getWeatherForDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return weatherService.getForecast(localDate);
    }
}
