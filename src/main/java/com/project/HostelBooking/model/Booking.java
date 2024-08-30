package com.project.HostelBooking.model;

import com.project.HostelBooking.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String toString(){
        return "Booking ID: " + id + "\n" +
                "Check In Date: " + checkInDate + "\n" +
                "Check Out Date: " + checkOutDate + "\n" +
                "Room: " + room.getName() + "\n" +
                "User: " + user.getUsername() + "\n";
    }
}
