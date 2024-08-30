package com.project.HostelBooking.mappers;

import com.project.HostelBooking.model.Hotel;
import com.project.HostelBooking.web.dto.hotel.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RoomMapper.class})
public interface HotelMapper {

    Hotel hotelCreateToHotel(HotelCreateRequest request);

    @Mapping(source = "hotelId", target = "id")
    Hotel hotelUpdateToHotel(Long hotelId, HotelUpdateRequest request);

    HotelResponse hotelToResponse(Hotel hotel);

    HotelUpdateResponse hotelToUpdateResponse(Hotel hotel);

    default HotelListResponse hotelListToResponseList(List<Hotel> hotels, int totalHotelsCount) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotels.stream().map(this::hotelToResponse).collect(Collectors.toList()));
        response.setTotalHotelsCount(totalHotelsCount);
        return response;
    }
}
