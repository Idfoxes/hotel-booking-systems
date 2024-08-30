package com.project.HostelBooking.web.dto.hotel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelListResponse {

    private List<HotelResponse> hotels = new ArrayList<>();

    private Integer totalHotelsCount;
}
