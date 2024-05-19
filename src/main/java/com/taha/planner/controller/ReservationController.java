package com.taha.planner.controller;

import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
        try {
            Room availableRoom = roomReservationService.findAvailableRoom(startTime, endTime, meetingType, attendees);
            if (availableRoom == null) {
                return new ResponseEntity<>("No available room found for the given criteria.", HttpStatus.NOT_FOUND);
            }
            Reservation reservation = roomReservationService.makeReservation(availableRoom, startTime, endTime, meetingType, attendees);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the reservation.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}