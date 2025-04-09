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
import tqs.hw1.model.ErrorResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final MealService mealService;
    private final ReservationService reservationService;
    private static final int MAX_RESERVATIONS_PER_DAY = 50; 

    public ReservationController(MealService mealService, ReservationService reservationService) {
        this.mealService = mealService;
        this.reservationService = reservationService;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @PostMapping("/book/{mealId}")
    public ResponseEntity<?> bookMeal(@PathVariable Long mealId) {
        Optional<Meal> mealOpt = mealService.getMealById(mealId);
        if (mealOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Meal not found"));
        }
        Meal meal = mealOpt.get();
        long existingReservations = reservationService.countReservationsForMealOnDate(meal.getDate(), meal.getRestaurant().getId());
        if (existingReservations >= MAX_RESERVATIONS_PER_DAY) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Restaurant is fully booked for this date"));
        }
        Reservation reservation = reservationService.createReservation(meal);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    
  

    @GetMapping("/{code}")
    public ResponseEntity<Reservation> checkReservation(@PathVariable String code) {
        return reservationService.getReservationByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/checkin/{code}")
    public ResponseEntity<String> checkInReservation(@PathVariable String code) {
        try {
            boolean success = reservationService.checkInReservation(code);
            if (success) {
                return ResponseEntity.ok("Check-in successful");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found or already used");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found or already used");
        }
        
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
