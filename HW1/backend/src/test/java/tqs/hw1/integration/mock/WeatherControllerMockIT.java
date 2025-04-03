package tqs.hw1.controller.integration.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
public class WeatherControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");


    @Test
    @DisplayName("GET /weather/{city}/{date} returns weather conditions for a given city and date")
    void whenGetWeatherForCityAndDate_thenReturnWeather() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-03";
        
        String expectedCondition = "Rain, Partially cloudy";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("days[0].conditions").value(expectedCondition));

        // Second request should hit the cache
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/current returns current weather conditions")
    void whenGetWeatherDaysAndCurrent_thenReturnCorrectData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-03";
        
        String expectedCurrentCondition = "Partially cloudy";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date + "/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currentConditions.conditions").value(expectedCurrentCondition));
        
        // Second request should hit the cache
        mvc.perform(get("/weather/" + city + "/" + date))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/alerts returns weather alerts")
    void whenGetWeatherAlerts_thenReturnAlerts() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-03";
        
        mvc.perform(get("/weather/" + city + "/" + date + "/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("alerts").isEmpty());
        // Second request should hit the cache
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/hours returns hourly weather data")
    void whenGetWeatherHours_thenReturnHourlyData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-03";
        
        String expectedHourCondition = "Partially cloudy";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date + "/hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].conditions").value(expectedHourCondition));
        // Second request should hit the cache
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/cache/stats returns cache statistics")
    void whenGetCacheStats_thenReturnCorrectStats() throws Exception {
        mvc.perform(get("/weather/cache/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Total Requests")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Cache Hits")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Cache Misses")));
        
    }
}
