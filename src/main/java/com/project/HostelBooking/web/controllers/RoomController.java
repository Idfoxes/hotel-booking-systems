package com.project.HostelBooking.web.controllers;

import com.project.HostelBooking.services.RoomService;
import com.project.HostelBooking.web.dto.room.RoomFilterRequest;
import com.project.HostelBooking.web.dto.room.RoomListResponse;
import com.project.HostelBooking.web.dto.room.RoomRequest;
import com.project.HostelBooking.web.dto.room.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/filter/{pageNumber}/{pageSize}")
    public ResponseEntity<RoomListResponse> getRoomsWithFilter(@RequestBody @Valid RoomFilterRequest filter,
                                                               @PathVariable int pageNumber,
                                                               @PathVariable int pageSize){
        return ResponseEntity.ok(roomService.getRoomsWithFilter(filter, pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid RoomRequest request){
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomRequest request){
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
