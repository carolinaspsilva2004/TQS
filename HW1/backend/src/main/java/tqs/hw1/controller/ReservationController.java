package tqs.hw1.controller;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.service.ReservationService;
import tqs.hw1.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final MealService mealService;
    private final ReservationService reservationService;

    public ReservationController(MealService mealService, ReservationService reservationService) {
        this.mealService = mealService;
        this.reservationService = reservationService;
    }

    @PostMapping("/book/{mealId}")
    public ResponseEntity<?> bookMeal(@PathVariable Long mealId) {
        Optional<Meal> meal = mealService.getMealById(mealId);
        if (meal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Meal not found\"}");
        }
        Reservation reservation = reservationService.createReservation(meal.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }
    

    @GetMapping("/{code}")
    public ResponseEntity<Reservation> checkReservation(@PathVariable String code) {
        return reservationService.getReservationByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/checkin/{code}")
    public ResponseEntity<String> checkIn(@PathVariable String code) {
        boolean success = reservationService.checkInReservation(code);
        if (success) {
            return ResponseEntity.ok("Check-in successful");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found or already used");
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteReservation(@PathVariable String code) {
        boolean deleted = reservationService.deleteReservationByCode(code);
        if (deleted) {
            return ResponseEntity.ok("Reservation deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
    }

}
