package tqs.carsservice;

import tqs.carsservice.model.Car;
import tqs.carsservice.service.CarManagerService;
import tqs.carsservice.controller.CarController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    public void shouldReturnListOfCars() throws Exception {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        Mockito.when(carManagerService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is("Toyota")))
                .andExpect(jsonPath("$[1].model", is("Civic")));
    }

    @Test
    public void shouldReturnCarById() throws Exception {
        Car car = new Car("Ford", "Fiesta");
        Mockito.when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Ford")));
    }

    @Test
    public void shouldReturnNotFoundForInvalidId() throws Exception {
        Mockito.when(carManagerService.getCarDetails(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/99"))
                .andExpect(status().isNotFound());
    }
}
