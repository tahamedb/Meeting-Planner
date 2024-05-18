package com.taha.planner.repository;

import com.taha.planner.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    public List<Reservation> findByRoomIdAndAndStartBetween(Long roomId, LocalDateTime start, LocalDateTime end);
}
