package tqs.hw1.controller;

import tqs.hw1.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckInController {
    private final ReservationService reservationService;

    public CheckInController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{code}")
    public boolean checkInReservation(@PathVariable String code) {
        return reservationService.checkInReservation(code);
    }
}