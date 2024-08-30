package com.project.HostelBooking.services;

import com.project.HostelBooking.exceptions.HotelNotFoundException;
import com.project.HostelBooking.exceptions.RoomNotFoundException;
import com.project.HostelBooking.mappers.RoomMapper;
import com.project.HostelBooking.model.Hotel;
import com.project.HostelBooking.model.Room;
import com.project.HostelBooking.repositories.jpa.HotelRepository;
import com.project.HostelBooking.repositories.jpa.RoomRepository;
import com.project.HostelBooking.repositories.jpa.RoomSpecification;
import com.project.HostelBooking.utils.BeanUtils;
import com.project.HostelBooking.web.dto.room.RoomFilterRequest;
import com.project.HostelBooking.web.dto.room.RoomListResponse;
import com.project.HostelBooking.web.dto.room.RoomRequest;
import com.project.HostelBooking.web.dto.room.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;

    public RoomListResponse getRoomsWithFilter(RoomFilterRequest filter, int page, int size){
        return roomMapper.roomListToResponseList(roomRepository.findAll(
                RoomSpecification.findWithFilter(filter), PageRequest.of(page, size)
        ).getContent());
    }

    public RoomResponse getRoomById(Long id) {
        return roomMapper.roomToResponse(roomRepository.findById(id).
                orElseThrow(() -> new RoomNotFoundException(
                        MessageFormat.format("Комната с id {0} не найдена.", id)
                )));
    }

    public RoomResponse createRoom(RoomRequest request){
        Room newRoom = roomMapper.roomCreateToRoom(request);
        Hotel hotel = hotelRepository.findById(Long.valueOf(request.getHotelId()))
                .orElseThrow(() -> new HotelNotFoundException(
                        MessageFormat.format("Отель с id {0} не найден.", request.getHotelId()
                        )));
        newRoom.setHotel(hotel);
        return roomMapper.roomToResponse(roomRepository.save(newRoom));
    }

    public RoomResponse updateRoom(Long id, RoomRequest request){
        Room updateRoom = roomMapper.roomUpdateToRoom(id, request);
        Optional<Room> excitedRoom = roomRepository.findById(id);

        if (excitedRoom.isPresent()) {
            BeanUtils.copyNonNullProperties(updateRoom, excitedRoom.get());
            return roomMapper.roomToResponse(roomRepository.save(excitedRoom.get()));
        }
        throw new RoomNotFoundException(MessageFormat.format("Комната с id {0} не найдена.", id));
    }

    public void deleteRoom(Long id){
        Optional<Room> excitedRoom = roomRepository.findById(id);
        if (excitedRoom.isPresent()) {
            roomRepository.deleteById(id);
        } else {
            throw new RoomNotFoundException(MessageFormat.format("Комната с id {0} не найдена.", id));
        }
    }

}
