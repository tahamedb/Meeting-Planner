package com.taha.planner.controller;

import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService roomReservationService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveRoom(@RequestParam LocalDateTime startTime,
                                         @RequestParam LocalDateTime endTime,
                                         @RequestParam String meetingType,
                                         @RequestParam int attendees) {
        Map<String, Object> response = new HashMap<>();
        try {
            Room availableRoom = roomReservationService.findAvailableRoom(startTime, endTime, meetingType, attendees);
            if (availableRoom == null) {
                response.put("message", "No available room found for the given criteria.");
                return ResponseEntity.ok(response);
            }
            Reservation reservation = roomReservationService.makeReservation(availableRoom, startTime, endTime, meetingType, attendees);
            return ResponseEntity.ok(reservation);
        }catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "An error occurred while processing the reservation.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
