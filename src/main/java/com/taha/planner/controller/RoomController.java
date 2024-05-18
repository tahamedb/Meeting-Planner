package com.taha.planner.controller;

import com.taha.planner.model.Room;
import com.taha.planner.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Rooms")
public class RoomController {
    @Autowired
    RoomService roomService;
    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
     roomService.addRoom(room);
     return ResponseEntity.ok(room);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms=roomService.getAllRooms();
        return  ResponseEntity.ok(rooms);
    }

}
