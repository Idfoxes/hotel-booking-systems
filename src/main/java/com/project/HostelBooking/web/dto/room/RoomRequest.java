package com.project.HostelBooking.web.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequest {

    @NotBlank(message = "Название комнаты не должно быть пустым.")
    private String name;

    @NotBlank(message = "Описание комнаты не должно быть пустым.")
    private String description;

    @NotNull(message = "Поле с номером комнаты не должно быть пустым.")
    @PositiveOrZero(message = "Поле с номером комнаты должно быть положительным числом.")
    private Integer number;

    @NotNull(message = "Поле со стоимостью комнаты не должно быть пустым.")
    @PositiveOrZero(message = "Поле со стоимостью комнаты должно быть положительным числом.")
    private BigDecimal price;

    @NotNull(message = "Поле с максимальным количеством людей не должно быть пустым.")
    @PositiveOrZero(message = "Поле с максимальным количеством людей должно быть положительным числом.")
    private Integer maxPeopleCount;

    @NotNull(message = "Поле с id отеля не должно быть пустым.")
    private String hotelId;
}
