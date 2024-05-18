package com.taha.planner.controller;

import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Reservation reserveRoom(@RequestParam LocalDateTime startTime,
                                   @RequestParam LocalDateTime endTime,
                                   @RequestParam String meetingType,
                                   @RequestParam int attendees) {
        Room availableRoom = roomReservationService.findAvailableRoom(startTime, endTime, meetingType, attendees);
        if (availableRoom == null) {
            throw new RuntimeException("No available room found for the given criteria.");
        }
        return roomReservationService.makeReservation(availableRoom, startTime, endTime, meetingType, attendees);
    }
}