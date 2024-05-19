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
public boolean isRoomAvailable(Room room, LocalDateTime start, LocalDateTime end, int participants, String meetingType){
    System.out.println("dkhlt l is room available");
    Set<String> requiredEq=equipmentRequirements.get(meetingType);
    System.out.println(requiredEq);
for(String equipment : requiredEq){
    System.out.println(room.getEquipment());
    boolean hasEquipment=room.getEquipment().stream().anyMatch(e->e.getName().equals(equipment));
    if(!hasEquipment)return false;
}
    List<Reservation> reservations = resaRepo.findByRoomIdAndAndStartBetween(room.getId(), start.minusHours(1), end.plusHours(1));
    return reservations.isEmpty();
}


    public Room findAvailableRoom(LocalDateTime startTime, LocalDateTime endTime, String meetingType, int attendees) {
        // Fetch only rooms that can accommodate the number of attendees
        System.out.println("dkhlt l findavailable room");
        List<Room> rooms = roomRepo.findRoomsByCapacity(attendees);
        System.out.println(roomRepo.findById(3L));
        System.out.println(rooms.get(0));

        // Sort rooms by capacity and number of equipment
        rooms = rooms.stream()
                .sorted(Comparator.comparingInt((Room room) -> room.getCapacity())
                        .thenComparingInt(room -> room.getEquipment().size()))
                .collect(Collectors.toList());
        System.out.println(rooms);
        for (Room room : rooms) {
            if ("RS".equals(meetingType) && room.getCapacity() < 3) {
                continue;
            }
            if (isRoomAvailable(room, startTime, endTime, attendees, meetingType)) {
                return room;
            }
        }
        return null;
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
