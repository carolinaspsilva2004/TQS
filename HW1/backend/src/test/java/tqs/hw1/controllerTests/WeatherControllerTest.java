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
        System.out.println("🔍 Iniciando teste: testGetWeatherForCityAndDate");

        WeatherResponse.currentConditions mockCurrentConditions = new WeatherResponse.currentConditions("18", "Rainy", "80%");
        WeatherResponse.Day mockDay = new WeatherResponse.Day("2025-04-03", "18", "Rainy", null);
        WeatherResponse mockResponse = new WeatherResponse(List.of(mockDay), mockCurrentConditions, null);

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        System.out.println("📡 A fazer chamada GET para /weather/" + city + "/" + date);
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("days[0].conditions").value("Rainy"))
                .andExpect(jsonPath("currentConditions.conditions").value("Rainy"));

        verify(weatherService, times(1)).getForecast(city, date);
        System.out.println("✅ Chamada ao serviço verificada (1 chamada)");
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/current returns current weather conditions")
    void testGetWeatherCurrent() throws Exception {
        System.out.println("🔍 Iniciando teste: testGetWeatherCurrent");

        WeatherResponse.currentConditions mockCurrentConditions = new WeatherResponse.currentConditions("18", "Partially cloudy", "70%");
        WeatherResponse mockResponse = new WeatherResponse(null, mockCurrentConditions, null);

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        System.out.println("📡 A fazer chamada GET para /weather/" + city + "/" + date + "/current");
        mvc.perform(get("/weather/" + city + "/" + date + "/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currentConditions.conditions").value("Partially cloudy"));

        System.out.println("✅ Teste concluído: currentConditions verificados");
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/alerts returns weather alerts")
    void testGetWeatherAlerts() throws Exception {
        System.out.println("🔍 Iniciando teste: testGetWeatherAlerts");

        WeatherResponse.Alert mockAlert = new WeatherResponse.Alert("Severe storm warning", "High", "Aveiro");
        WeatherResponse mockResponse = new WeatherResponse(null, null, List.of(mockAlert));

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        System.out.println("📡 A fazer chamada GET para /weather/" + city + "/" + date + "/alerts");
        mvc.perform(get("/weather/" + city + "/" + date + "/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("alerts[0].description").value("Severe storm warning"))
                .andExpect(jsonPath("alerts[0].severity").value("High"))
                .andExpect(jsonPath("alerts[0].area").value("Aveiro"));

        System.out.println("✅ Alertas verificados corretamente");
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/hours returns hourly weather data")
    void testGetWeatherHours() throws Exception {
        System.out.println("🔍 Iniciando teste: testGetWeatherHours");

        WeatherResponse.Day.Hours mockHour = new WeatherResponse.Day.Hours("2025-04-03T12:00:00", "18", "Rainy");
        WeatherResponse.Day mockDay = new WeatherResponse.Day("2025-04-03", "18", "Rainy", List.of(mockHour));
        WeatherResponse mockResponse = new WeatherResponse(List.of(mockDay), null, null);

        when(weatherService.getForecast(city, date)).thenReturn(mockResponse);

        System.out.println("📡 A fazer chamada GET para /weather/" + city + "/" + date + "/hours");
        mvc.perform(get("/weather/" + city + "/" + date + "/hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].conditions").value("Rainy"))
                .andExpect(jsonPath("[0].datetime").value("2025-04-03T12:00:00"));

        System.out.println("✅ Dados horários verificados com sucesso");
    }

    @Test
    @DisplayName("GET /weather/cache/stats returns cache statistics")
    void testGetCacheStats() throws Exception {
        System.out.println("🔍 Iniciando teste: testGetCacheStats");

        when(weatherService.getTotalRequests()).thenReturn(5);
        when(weatherService.getCacheHits()).thenReturn(3);
        when(weatherService.getCacheMisses()).thenReturn(2);

        System.out.println("📡 A fazer chamada GET para /weather/cache/stats");
        mvc.perform(get("/weather/cache/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Total Requests: 5")))
                .andExpect(content().string(containsString("Cache Hits: 3")))
                .andExpect(content().string(containsString("Cache Misses: 2")));

        System.out.println("✅ Estatísticas de cache verificadas corretamente");
    }
}
