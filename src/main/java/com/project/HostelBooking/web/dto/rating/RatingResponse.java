package com.project.HostelBooking.web.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {

    private Long hotelId;

    private Integer mark;

    private Double rating;
}
