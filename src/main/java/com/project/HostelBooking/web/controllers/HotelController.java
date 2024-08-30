package com.project.HostelBooking.web.controllers;

import com.project.HostelBooking.services.HotelService;
import com.project.HostelBooking.web.dto.hotel.*;
import com.project.HostelBooking.web.dto.rating.RatingRequest;
import com.project.HostelBooking.web.dto.rating.RatingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<HotelListResponse> getAllHotels(@PathVariable int pageNumber, @PathVariable int pageSize){
        return ResponseEntity.ok(hotelService.getAllHotels(pageNumber, pageSize));
    }

    @GetMapping("/filter/{pageNumber}/{pageSize}")
    public ResponseEntity<HotelListResponse> getHotelsWithFilter(@RequestBody @Valid HotelFilterRequest filter,
                                                                 @PathVariable int pageNumber,
                                                                 @PathVariable int pageSize){
        return ResponseEntity.ok(hotelService.getHotelsWithFilter(filter, pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@RequestBody @Valid  HotelCreateRequest request){
        return ResponseEntity.ok(hotelService.createHotel(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelUpdateResponse> updateHotel(@PathVariable Long id, @RequestBody @Valid HotelUpdateRequest request){
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id){
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rating")
    public ResponseEntity<RatingResponse> addRating(@RequestBody @Valid RatingRequest request){
        return ResponseEntity.ok(hotelService.addRating(request));
    }
}
