package com.project.HostelBooking.web.dto.hotel;

import lombok.Data;

@Data
public class HotelUpdateResponse {

    private Long id;

    private String name;

    private String advertisement;

    private String city;

    private String address;

    private Integer distance;
}
