package com.project.HostelBooking.mappers;

import com.project.HostelBooking.model.Room;
import com.project.HostelBooking.model.UnavailableDate;
import com.project.HostelBooking.web.dto.room.RoomRequest;
import com.project.HostelBooking.web.dto.room.RoomListResponse;
import com.project.HostelBooking.web.dto.room.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room roomCreateToRoom(RoomRequest request);

    @Mapping(source = "roomId", target = "id")
    Room roomUpdateToRoom(Long roomId, RoomRequest request);

    @Mapping(target = "unavailableDates", source = "unavailableDates")
    RoomResponse roomToResponse(Room room);

    default RoomListResponse roomListToResponseList(List<Room> rooms) {
        RoomListResponse response = new RoomListResponse();
        response.setRooms(rooms.stream().map(this::roomToResponse).collect(Collectors.toList()));
        return response;
    }

    default List<LocalDate> map(List<UnavailableDate> unavailableDates) {
        return unavailableDates.stream()
                .map(UnavailableDate::getUnavailableDate)
                .collect(Collectors.toList());
    }

}
