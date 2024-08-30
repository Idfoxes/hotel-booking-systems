package com.project.HostelBooking.web.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class HotelCreateRequest {

    @NotBlank(message = "Название отеля не должно быть пустым.")
    private String name;

    @NotBlank(message = "Заголовок объявления не должен быть пустым.")
    private String advertisement;

    @NotBlank(message = "Поле с городом, в котором расположен отель, не должен быть пустым.")
    private String city;

    @NotBlank(message = "Адрес отеля не должен быть пустым.")
    private String address;

    @NotNull(message = "Поле с расстоянием от центра города не должно быть пустым.")
    @PositiveOrZero(message = "Поле с расстоянием от центра города должно быть положительным числом.")
    private Integer distance;

    @NotNull(message = "Поле с рейтингом не должно быть пустым.")
    @PositiveOrZero(message = "Поле с рейтингом должно быть положительным числом.")
    private Double rating;

    @NotNull(message = "Поле с количеством оценок не должно быть пустым.")
    @PositiveOrZero(message = "Поле с количеством оценок должно быть положительным числом.")
    private Integer markCount;
}
