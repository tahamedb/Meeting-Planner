package com.taha.planner.service;

import com.taha.planner.model.Equipment;
import com.taha.planner.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EquipmentService {
   @Autowired
    EquipmentRepository equipmentRepo;
        public Equipment addEquipment(Equipment equipment){
            return equipmentRepo.save(equipment);
        }
        public List<Equipment> getAllEquipments(){
            return equipmentRepo.findAll();
        }
        public Equipment getEquipmentById(Long id){
            return equipmentRepo.findById(id).orElse(null);
        }
        public void removeEquipment(Long id){
            equipmentRepo.deleteById(id);
        }
    }


