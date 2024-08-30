package com.project.HostelBooking.web.dto.room;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse {

    private Long id;

    private String name;

    private String description;

    private Integer number;

    private BigDecimal price;

    private Integer maxPeopleCount;

    private List<LocalDate> unavailableDates = new ArrayList<>();

}
