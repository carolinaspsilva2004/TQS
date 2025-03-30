package tqs.hw1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.hw1.model.Restaurant;
import tqs.hw1.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    @InjectMocks
    private RestaurantService restaurantService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of(new Restaurant("Testaurant", "http://menu.url")));
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        assertThat(restaurants).hasSize(1);
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testGetRestaurantById() {
        Restaurant restaurant = new Restaurant("Testaurant", "http://menu.url");
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Optional<Restaurant> foundRestaurant = restaurantService.getRestaurantById(1L);
        assertThat(foundRestaurant).isPresent().contains(restaurant);
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveRestaurant() {
        Restaurant restaurant = new Restaurant("New Place", "http://new.url");
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        assertThat(savedRestaurant).isEqualTo(restaurant);
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testDeleteRestaurant() {
        doNothing().when(restaurantRepository).deleteById(1L);
        restaurantService.deleteRestaurant(1L);
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExistsById() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        boolean exists = restaurantService.existsById(1L);
        assertThat(exists).isTrue();
        verify(restaurantRepository, times(1)).existsById(1L);
    }
}
