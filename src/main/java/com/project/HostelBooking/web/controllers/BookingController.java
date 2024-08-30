package com.project.HostelBooking.web.controllers;

import com.project.HostelBooking.services.BookingService;
import com.project.HostelBooking.web.dto.booking.BookingListResponse;
import com.project.HostelBooking.web.dto.booking.BookingRequest;
import com.project.HostelBooking.web.dto.booking.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<BookingListResponse> getAllBooking(@PathVariable int pageNumber, @PathVariable int pageSize){
        return ResponseEntity.ok(bookingService.getAllBookings(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> addBooking(@RequestBody @Valid BookingRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(bookingService.addBooking(request, userDetails.getUsername()));
    }
}
