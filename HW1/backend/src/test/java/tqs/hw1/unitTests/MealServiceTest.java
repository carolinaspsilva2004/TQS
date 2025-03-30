package tqs.hw1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.hw1.model.Meal;
import tqs.hw1.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MealServiceTest {
    
    @Mock
    private MealRepository mealRepository;
    
    @InjectMocks
    private MealService mealService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMeals() {
        when(mealRepository.findAll()).thenReturn(List.of(new Meal("Pizza", LocalDate.now(), null)));
        List<Meal> meals = mealService.getAllMeals();
        assertThat(meals).hasSize(1);
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void testGetMealById() {
        Meal meal = new Meal("Burger", LocalDate.now(), null);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        Optional<Meal> foundMeal = mealService.getMealById(1L);
        assertThat(foundMeal).isPresent().contains(meal);
        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveMeal() {
        Meal meal = new Meal("Sushi", LocalDate.now(), null);
        when(mealRepository.save(meal)).thenReturn(meal);
        Meal savedMeal = mealService.saveMeal(meal);
        assertThat(savedMeal).isEqualTo(meal);
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void testDeleteMeal() {
        doNothing().when(mealRepository).deleteById(1L);
        mealService.deleteMeal(1L);
        verify(mealRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExistsById() {
        when(mealRepository.existsById(1L)).thenReturn(true);
        boolean exists = mealService.existsById(1L);
        assertThat(exists).isTrue();
        verify(mealRepository, times(1)).existsById(1L);
    }
}