package tqs.hw1.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import tqs.hw1.controller.WeatherController;
import tqs.hw1.service.WeatherService;
import tqs.hw1.model.WeatherResponse;
import tqs.hw1.model.WeatherResponse.Day;
import tqs.hw1.model.WeatherResponse.currentConditions;
import tqs.hw1.model.WeatherResponse.Alert;
import tqs.hw1.model.WeatherResponse.Day.Hours;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    private final String city = "Aveiro";
    private final String date = "2025-04-03";

    @Test
    @DisplayName("GET /weather/{city}/{date} returns weather conditions")
    void testGetWeatherForCityAndDate() throws Exception {
        // Mock currentConditions and Day data
        WeatherResponse.currentConditions mockCurrentConditions = new WeatherResponse.currentConditions("18", "Rainy", "80%");
        WeatherResponse.Day mockDay = new WeatherResponse.Day("2025-04-03", "18", "Rainy", null);
        WeatherResponse mockResponse = new WeatherResponse(List.of(mockDay), mockCurrentConditions, null);

        // Mocking service response
        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        // Perform GET request and check response
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("days[0].conditions").value("Rainy"))
                .andExpect(jsonPath("currentConditions.conditions").value("Rainy"));

        // Verify the service call was made
        verify(weatherService, times(1)).getForecast(city, date);
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/current returns current weather conditions")
    void testGetWeatherCurrent() throws Exception {
        // Mock current weather response
        WeatherResponse.currentConditions mockCurrentConditions = new WeatherResponse.currentConditions("18", "Partially cloudy", "70%");
        WeatherResponse mockResponse = new WeatherResponse(null, mockCurrentConditions, null);

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        // Perform GET request and check response
        mvc.perform(get("/weather/" + city + "/" + date + "/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currentConditions.conditions").value("Partially cloudy"));
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/alerts returns weather alerts")
    void testGetWeatherAlerts() throws Exception {
        // Mock alerts response
        WeatherResponse.Alert mockAlert = new WeatherResponse.Alert("Severe storm warning", "High", "Aveiro");
        WeatherResponse mockResponse = new WeatherResponse(null, null, List.of(mockAlert));

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        // Perform GET request and check response
        mvc.perform(get("/weather/" + city + "/" + date + "/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("alerts[0].description").value("Severe storm warning"))
                .andExpect(jsonPath("alerts[0].severity").value("High"))
                .andExpect(jsonPath("alerts[0].area").value("Aveiro"));
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/hours returns hourly weather data")
    void testGetWeatherHours() throws Exception {
        // Mock hourly weather data
        WeatherResponse.Day.Hours mockHour = new WeatherResponse.Day.Hours("2025-04-03T12:00:00", "18", "Rainy");
        WeatherResponse.Day mockDay = new WeatherResponse.Day("2025-04-03", "18", "Rainy", List.of(mockHour));
        WeatherResponse mockResponse = new WeatherResponse(List.of(mockDay), null, null);

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        // Perform GET request and check response
        mvc.perform(get("/weather/" + city + "/" + date + "/hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].conditions").value("Rainy"))
                .andExpect(jsonPath("[0].datetime").value("2025-04-03T12:00:00"));
    }

    @Test
    @DisplayName("GET /weather/cache/stats returns cache statistics")
    void testGetCacheStats() throws Exception {
        // Mock cache statistics response
        when(weatherService.getTotalRequests()).thenReturn(5);
        when(weatherService.getCacheHits()).thenReturn(3);
        when(weatherService.getCacheMisses()).thenReturn(2);

        mvc.perform(get("/weather/cache/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Total Requests: 5")))
                .andExpect(content().string(containsString("Cache Hits: 3")))
                .andExpect(content().string(containsString("Cache Misses: 2")));
    }
}
