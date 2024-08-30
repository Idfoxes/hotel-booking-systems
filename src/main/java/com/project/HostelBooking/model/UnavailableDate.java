package com.project.HostelBooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity(name = "unavailable_date")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnavailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate unavailableDate;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public String toString(){
        return unavailableDate.toString();
    }
}
