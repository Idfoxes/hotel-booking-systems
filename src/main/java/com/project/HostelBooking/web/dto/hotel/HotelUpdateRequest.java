package com.project.HostelBooking.web.dto.hotel;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class HotelUpdateRequest {

    private String name;

    private String advertisement;

    private String city;

    private String address;

    @PositiveOrZero(message = "Поле с расстоянием от центра города должно быть положительным числом.")
    private Integer distance;
}
