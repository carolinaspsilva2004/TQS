package tqs.hw1.controller;

import tqs.hw1.model.Meal;
import tqs.hw1.model.Reservation;
import tqs.hw1.repository.MealRepository;
import tqs.hw1.service.ReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
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
    public ResponseEntity<Reservation> bookMeal(@PathVariable Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found"));
        Reservation reservation = reservationService.createReservation(meal);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Reservation> checkReservation(@PathVariable String code) {
        Optional<Reservation> reservation = reservationService.getReservationByCode(code);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/checkin/{code}")
    public ResponseEntity<String> checkIn(@PathVariable String code) {
        boolean result = reservationService.checkInReservation(code);
        if (result) {
            return ResponseEntity.ok("Check-in successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found or already used");
        }
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getReservations();
    }
}
