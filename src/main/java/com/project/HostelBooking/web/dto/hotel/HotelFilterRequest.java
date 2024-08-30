package com.project.HostelBooking.web.dto.hotel;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class HotelFilterRequest {

    private Long hotelId;

    private String hotelName;

    private String advertisement;

    private String city;

    private String address;

    @PositiveOrZero(message = "Поле с расстоянием от центра города должно быть положительным числом.")
    private Integer distance;

    @PositiveOrZero(message = "Поле с рейтингом должно быть положительным числом.")
    private Double rating;

    @PositiveOrZero(message = "Поле с количеством оценок должно быть положительным числом.")
    private Integer markCount;
}
