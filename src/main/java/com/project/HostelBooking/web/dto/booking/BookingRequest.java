package com.project.HostelBooking.web.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    @NotNull(message = "Поле с датой заезда не должно быть пустым.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkInDate;

    @NotNull(message = "Поле с датой выезда не должно быть пустым.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate checkOutDate;

    @NotNull(message = "Поле с id комнаты не должно быть пустым.")
    private Long roomId;
}
