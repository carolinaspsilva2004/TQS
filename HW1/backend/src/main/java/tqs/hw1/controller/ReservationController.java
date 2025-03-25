package tqs.hw1.controller;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final MealRepository mealRepository;
    private final ReservationService reservationService;

    public ReservationController(MealRepository mealRepository, ReservationService reservationService) {
        this.mealRepository = mealRepository;
        this.reservationService = reservationService;
    }

    @PostMapping("/book/{mealId}")
    public Reservation bookMeal(@PathVariable Long mealId) {
        Meal meal = mealRepository.findById(mealId).orElseThrow();
        return reservationService.createReservation(meal);
    }

    @GetMapping("/{code}")
    public Optional<Reservation> checkReservation(@PathVariable String code) {
        return reservationService.getReservationByCode(code);
    }

    @PostMapping("/checkin/{code}")
    public boolean checkIn(@PathVariable String code) {
        return reservationService.checkInReservation(code);
    }
}