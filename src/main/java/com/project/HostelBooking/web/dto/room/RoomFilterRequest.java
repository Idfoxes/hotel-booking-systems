package com.project.HostelBooking.web.dto.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomFilterRequest {

    private Long roomId;

    private String roomName;

    @PositiveOrZero(message = "Поле с минимальной стоимостью номера должно быть положительным числом.")
    private Integer minCost;

    @PositiveOrZero(message = "Поле с максимальной стоимостью номера должно быть положительным числом.")
    private Integer maxCost;

    @PositiveOrZero(message = "Поле с максимальным количеством людей в номере должно быть положительным числом.")
    private Integer maxPeopleCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkInDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkOutDate;
}
