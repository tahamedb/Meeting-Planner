package com.taha.planner.service;

import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.repository.ReservationRepository;
import com.taha.planner.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ReservationService {
    @Autowired
    ReservationRepository resaRepo;
    @Autowired
    RoomRepository roomRepo;
    private static final Map<String, Set<String>> equipmentRequirements = Map.of(
            "VC", Set.of("Screen", "Pieuvre", "Webcam"),
            "SPEC", Set.of("Whiteboard"),
            "RS", Set.of()
    );
public boolean isRoomAvailable(Room room, LocalDateTime start, LocalDateTime end, String meetingType){
    Set<String> requiredEq=equipmentRequirements.get(meetingType);
for(String equipment : requiredEq){
    boolean hasEquipment=room.getEquipment().stream().anyMatch(e->e.getName().equals(equipment));
    if(!hasEquipment)return false;}
    List<Reservation> reservations = resaRepo.findByRoomIdAndAndStartBetween(room.getId(), start.minusHours(1), end.plusHours(1));
    return reservations.isEmpty();
}


    public Room findAvailableRoom(LocalDateTime startTime, LocalDateTime endTime, String meetingType, int attendees) {
        if (!isValidMeetingType(meetingType)) {
            throw new IllegalArgumentException("Invalid meeting type: " + meetingType);
        }        // Fetch only rooms that can accommodate the number of attendees
        List<Room> rooms = roomRepo.findRoomsByCapacity(attendees);
        if (rooms.isEmpty()) return null;
        // Sort rooms by capacity and number of equipment
        rooms = rooms.stream()
                .sorted(Comparator.comparingInt((Room room) -> room.getCapacity())
                        .thenComparingInt(room -> room.getEquipment().size()))
                .collect(Collectors.toList());
        for (Room room : rooms) {
            if ("RS".equals(meetingType) && room.getCapacity() < 3) {
                continue;
            }
            if (isRoomAvailable(room, startTime, endTime, meetingType)) {
                return room;
            }
        }
        return null;
    }
    private boolean isValidMeetingType(String meetingType) {
        return meetingType.equals("VC") || meetingType.equals("RS") || meetingType.equals("SPEC") ;
    }
    public Reservation makeReservation(Room room, LocalDateTime startTime, LocalDateTime endTime, String meetingType, int attendees) {
        Reservation reservation = new Reservation();
        reservation.setRoomId(room.getId());
        reservation.setStart(startTime);
        reservation.setEnd(endTime);
        reservation.setType(meetingType);
        reservation.setAttendees(attendees);
        return resaRepo.save(reservation);
    }
    public Reservation addReservation(Reservation reservation){
        return resaRepo.save(reservation);
    }
    public List<Reservation> getAllReservations(){
        return resaRepo.findAll();
    }
    public Reservation getReservationById(Long id){
        return resaRepo.findById(id).orElse(null);
    }
    public void removeReservation(Long id){
        resaRepo.deleteById(id);
    }
}
