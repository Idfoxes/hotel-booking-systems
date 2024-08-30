package com.project.HostelBooking.web.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {

    @NotNull(message = "Укажите id отеля")
    private Long hotelId;

    @Min(value = 1, message = "Оценка должна быть больше 1.")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private Integer mark;
}
