package tqs.hw1.controller;

import tqs.hw1.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@RequiredArgsConstructor
public class CheckInController {
    private final ReservationService reservationService;

    @PostMapping("/{code}")
    public boolean checkInReservation(@PathVariable String code) {
        return reservationService.checkInReservation(code);
    }
}