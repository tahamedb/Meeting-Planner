package com.taha.planner.service;

import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.repository.ReservationRepository;
import com.taha.planner.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository resaRepo;

    @Mock
    private RoomRepository roomRepo;

    @InjectMocks
    private ReservationService reservationService;

    private Room room;
    private Reservation reservation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setCapacity(10);
        room.setEquipment(List.of());

        reservation = new Reservation();
        reservation.setRoomId(room.getId());
        reservation.setStart(startTime);
        reservation.setEnd(endTime);
        reservation.setType("VC");
        reservation.setAttendees(5);

        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(1);
    }

    @Test
    void testIsRoomAvailable_noReservations() {
        when(resaRepo.findByRoomIdAndAndStartBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        boolean isAvailable = reservationService.isRoomAvailable(room, startTime, endTime, 5, "RS");

        assertTrue(isAvailable);
        verify(resaRepo, times(1)).findByRoomIdAndAndStartBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testIsRoomAvailable_withReservations() {
        when(resaRepo.findByRoomIdAndAndStartBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(reservation));

        boolean isAvailable = reservationService.isRoomAvailable(room, startTime, endTime, 5, "RS");

        assertFalse(isAvailable);
        verify(resaRepo, times(1)).findByRoomIdAndAndStartBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testFindAvailableRoom() {
        when(roomRepo.findRoomsByCapacity(anyInt())).thenReturn(List.of(room));
        when(resaRepo.findByRoomIdAndAndStartBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        Room availableRoom = reservationService.findAvailableRoom(startTime, endTime, "RS", 5);

        assertNotNull(availableRoom);
        assertEquals(room.getId(), availableRoom.getId());
        verify(roomRepo, times(1)).findRoomsByCapacity(anyInt());
    }

    @Test
    void testMakeReservation() {
        System.out.println(startTime);
        when(resaRepo.save(any(Reservation.class))).thenReturn(reservation);

        Reservation newReservation = reservationService.makeReservation(room, startTime, endTime, "VC", 5);
        System.out.println(newReservation);
        assertNotNull(newReservation);
        assertEquals(room.getId(), newReservation.getRoomId());
//        assertEquals(startTime, newReservation.getStart());
//        assertEquals(endTime, newReservation.getEnd());
        assertEquals("VC", newReservation.getType());
        assertEquals(5, newReservation.getAttendees());
        verify(resaRepo, times(1)).save(any(Reservation.class));
    }

    @Test
    void testAddReservation() {
        when(resaRepo.save(any(Reservation.class))).thenReturn(reservation);

        Reservation addedReservation = reservationService.addReservation(reservation);

        assertNotNull(addedReservation);
        assertEquals(reservation.getRoomId(), addedReservation.getRoomId());
        verify(resaRepo, times(1)).save(any(Reservation.class));
    }

    @Test
    void testGetAllReservations() {
        when(resaRepo.findAll()).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.getAllReservations();

        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
        assertEquals(reservation.getRoomId(), reservations.get(0).getRoomId());
        verify(resaRepo, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        when(resaRepo.findById(anyLong())).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.getReservationById(1L);

        assertNotNull(foundReservation);
        assertEquals(reservation.getRoomId(), foundReservation.getRoomId());
        verify(resaRepo, times(1)).findById(anyLong());
    }

    @Test
    void testRemoveReservation() {
        doNothing().when(resaRepo).deleteById(anyLong());

        reservationService.removeReservation(1L);

        verify(resaRepo, times(1)).deleteById(anyLong());
    }
}
