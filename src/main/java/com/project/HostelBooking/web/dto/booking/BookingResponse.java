package com.project.HostelBooking.web.dto.booking;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long roomId;

    private Long userId;
}
