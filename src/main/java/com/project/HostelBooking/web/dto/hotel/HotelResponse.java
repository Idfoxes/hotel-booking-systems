package com.project.HostelBooking.web.dto.hotel;

import com.project.HostelBooking.web.dto.room.RoomResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelResponse {

    private Long id;

    private String name;

    private String advertisement;

    private String city;

    private String address;

    private Integer distance;

    private Double rating;

    private Integer markCount;

    private List<RoomResponse> rooms = new ArrayList<>();
}
