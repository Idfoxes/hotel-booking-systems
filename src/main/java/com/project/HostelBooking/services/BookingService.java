package com.project.HostelBooking.services;

import com.project.HostelBooking.exceptions.RoomNotFoundException;
import com.project.HostelBooking.exceptions.UnavailableDateException;
import com.project.HostelBooking.mappers.BookingMapper;
import com.project.HostelBooking.model.Booking;
import com.project.HostelBooking.model.Room;
import com.project.HostelBooking.model.UnavailableDate;
import com.project.HostelBooking.model.events.BookingEvent;
import com.project.HostelBooking.model.user.User;
import com.project.HostelBooking.repositories.jpa.BookingRepository;
import com.project.HostelBooking.repositories.jpa.RoomRepository;
import com.project.HostelBooking.repositories.jpa.UserRepository;
import com.project.HostelBooking.web.dto.booking.BookingListResponse;
import com.project.HostelBooking.web.dto.booking.BookingRequest;
import com.project.HostelBooking.web.dto.booking.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Value("${app.kafka.kafkaMessageNewBookingTopic}")
    private String newBookingTopic;

    public BookingListResponse getAllBookings(int page, int size) {
        return bookingMapper.bookingListToResponseList(bookingRepository.findAll(PageRequest.of(page, size)).getContent());
    }

    public BookingResponse addBooking(BookingRequest bookingRequest, String username) {
        Booking booking = bookingMapper.requestToBooking(bookingRequest);
        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(
                () -> new RoomNotFoundException(
                        MessageFormat.format("Комната с id {0} не найдена.", bookingRequest.getRoomId()))
        );
        User user = userRepository.findByUsername(username);
        booking.setRoom(room);
        booking.setUser(user);
        if (!checkAvailableDates(booking)){
            throw new UnavailableDateException("Номер забронирован в выбранные даты.");
        }

        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setUserId(user.getId());
        bookingEvent.setCheckInDate(bookingRequest.getCheckInDate());
        bookingEvent.setCheckOutDate(bookingRequest.getCheckOutDate());
        kafkaTemplate.send(newBookingTopic, bookingEvent);

        return bookingMapper.bookingToBookingResponse(bookingRepository.save(booking));
    }

    private boolean checkAvailableDates(Booking booking) {
        Room room = booking.getRoom();
        List<UnavailableDate> unavailableDates = room.getUnavailableDates();
        List<LocalDate> dates = unavailableDates.stream().map(UnavailableDate::getUnavailableDate).toList();

        for (LocalDate date : dates) {
            if ((date.isAfter(booking.getCheckInDate()) && date.isBefore(booking.getCheckOutDate())) ||
                    date.isEqual(booking.getCheckInDate()) || date.isEqual(booking.getCheckOutDate())){
                return false;
            }
        }
        new UnavailableDate();
        unavailableDates.add(UnavailableDate
                .builder()
                .unavailableDate(booking.getCheckInDate())
                .room(room)
                .build());
        new UnavailableDate();
        unavailableDates.add(UnavailableDate
                .builder()
                .unavailableDate(booking.getCheckOutDate())
                .room(room)
                .build());
        return true;
    }

}
