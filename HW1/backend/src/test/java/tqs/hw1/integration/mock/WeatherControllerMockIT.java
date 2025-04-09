package tqs.hw1.controller.integration.mock;

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
        String date = "2025-04-10";
        String expectedCondition = "Partially cloudy";

        System.out.println("🌤️ Teste: quando pedimos previsão para cidade e data (" + city + ", " + date + ")");
        
        System.out.println("📡 Primeira chamada (espera-se chamada externa ou cache miss)");
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("days[0].conditions").value(expectedCondition));

        System.out.println("📦 Segunda chamada (espera-se cache hit)");
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/current returns current weather conditions")
    void whenGetWeatherDaysAndCurrent_thenReturnCorrectData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-10";
        String expectedCurrentCondition = "Partially cloudy";

        System.out.println("🌥️ Teste: previsão atual para " + city + " em " + date);

        System.out.println("📡 A chamar endpoint de condições atuais");
        mvc.perform(get("/weather/" + city + "/" + date + "/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currentConditions.conditions").value(expectedCurrentCondition));

        System.out.println("📦 Requisição subsequente para validar cache");
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/alerts returns weather alerts")
    void whenGetWeatherAlerts_thenReturnAlerts() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-10";

        System.out.println("⚠️ Teste: alertas meteorológicos para " + city + " em " + date);

        System.out.println("📡 A chamar endpoint de alertas");
        mvc.perform(get("/weather/" + city + "/" + date + "/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("alerts").isEmpty());

        System.out.println("📦 Requisição subsequente para validar cache");
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/{city}/{date}/hours returns hourly weather data")
    void whenGetWeatherHours_thenReturnHourlyData() throws Exception {
        String city = "Aveiro";
        String date = "2025-04-12";
        String expectedHourCondition = "Rain, Partially cloudy";

        System.out.println("🕓 Teste: dados horários para " + city + " em " + date);

        System.out.println("📡 A chamar endpoint de horas");
        mvc.perform(get("/weather/" + city + "/" + date + "/hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].conditions").value(expectedHourCondition));

        System.out.println("📦 Requisição subsequente para validar cache");
        mvc.perform(get("/weather/" + city + "/" + date))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /weather/cache/stats returns cache statistics")
    void whenGetCacheStats_thenReturnCorrectStats() throws Exception {
        System.out.println("📊 Teste: estatísticas do cache");

        mvc.perform(get("/weather/cache/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Total Requests")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Cache Hits")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Cache Misses")));

        System.out.println("✅ Estatísticas retornadas com sucesso");
    }
}
