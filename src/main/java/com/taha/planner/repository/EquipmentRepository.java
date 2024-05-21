package com.taha.planner.repository;

import com.taha.planner.model.Equipment;
import com.taha.planner.model.Reservation;
import com.taha.planner.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

}
