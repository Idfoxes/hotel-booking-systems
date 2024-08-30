package com.project.HostelBooking.mappers;

import com.project.HostelBooking.model.Booking;
import com.project.HostelBooking.web.dto.booking.BookingListResponse;
import com.project.HostelBooking.web.dto.booking.BookingRequest;
import com.project.HostelBooking.web.dto.booking.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(BookingRequest bookingRequest);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    BookingResponse bookingToBookingResponse(Booking booking);

    default BookingListResponse bookingListToResponseList(List<Booking> bookings) {
        BookingListResponse response = new BookingListResponse();
        response.setBookings(bookings.stream().map(this::bookingToBookingResponse).collect(Collectors.toList()));
        return response;
    }
}
