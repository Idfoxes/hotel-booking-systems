package com.project.HostelBooking.model.events;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "booking_events")
public class BookingEvent {

    @Id
    private String id;

    private Long userId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}
