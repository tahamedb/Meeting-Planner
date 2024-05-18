package com.taha.planner.service;

import com.taha.planner.model.Room;
import com.taha.planner.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepo;
    public Room addRoom(Room room){
        return roomRepo.save(room);
    }
    public List<Room> getAllRooms(){
        return roomRepo.findAll();
    }
    public Room getRoomById(Long id){
        return roomRepo.findById(id).orElse(null);
    }
    public void removeRoom(Long id){
         roomRepo.deleteById(id);
    }
}
