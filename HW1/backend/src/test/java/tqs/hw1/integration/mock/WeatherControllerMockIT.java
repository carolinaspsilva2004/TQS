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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class WeatherControllerMockIT {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        // Setup necessary data here, if needed
    }

    @Test
    @DisplayName("GET /weather/{city}/{date} returns weather conditions for a given city and date")
    void whenGetWeatherForCityAndDate_thenReturnWeather() throws Exception {
        String city = "Aveiro";
        String date = "2025-03-31";
        
        String expectedCondition = "Clear";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("days[0].conditions").value(expectedCondition));
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/current returns current weather conditions")
    void whenGetWeatherDaysAndCurrent_thenReturnCorrectData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-01";
        
        String expectedCurrentCondition = "Clear";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date + "/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currentConditions.conditions").value(expectedCurrentCondition));
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/alerts returns weather alerts")
    void whenGetWeatherAlerts_thenReturnAlerts() throws Exception {
        String city = "Aveiro";
        String date = "2025-03-31";
        
        mvc.perform(get("/weather/" + city + "/" + date + "/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("alerts").isEmpty());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/hours returns hourly weather data")
    void whenGetWeatherHours_thenReturnHourlyData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-01";
        
        String expectedHourCondition = "Partially cloudy";  // Use the correct expected condition
        
        mvc.perform(get("/weather/" + city + "/" + date + "/hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].conditions").value(expectedHourCondition));
    }
}
