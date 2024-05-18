package com.taha.planner.repository;

import com.taha.planner.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.capacity * 0.7 >= :attendees")
    List<Room> findRoomsByCapacity(@Param("attendees") int attendees);
    @Query("SELECT r FROM Room r JOIN FETCH r.equipment")
    List<Room> findAllWithEquipment();
     Optional<Room> findById(Long id);

}

