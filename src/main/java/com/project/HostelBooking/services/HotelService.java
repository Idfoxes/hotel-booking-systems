package com.project.HostelBooking.services;

import com.project.HostelBooking.exceptions.HotelNotFoundException;
import com.project.HostelBooking.mappers.HotelMapper;
import com.project.HostelBooking.model.Hotel;
import com.project.HostelBooking.repositories.jpa.HotelRepository;
import com.project.HostelBooking.repositories.jpa.HotelSpecification;
import com.project.HostelBooking.utils.BeanUtils;
import com.project.HostelBooking.web.dto.hotel.*;
import com.project.HostelBooking.web.dto.rating.RatingRequest;
import com.project.HostelBooking.web.dto.rating.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelListResponse getAllHotels(int page, int size) {
        return hotelMapper.hotelListToResponseList(hotelRepository.findAll(PageRequest.of(page, size)).getContent(),
                hotelRepository.findAll().size());
    }

    public HotelResponse getHotelById(Long id) {
        return hotelMapper.hotelToResponse(getHotel(id));
    }

    public HotelResponse createHotel(HotelCreateRequest request){
        Hotel newHotel = hotelMapper.hotelCreateToHotel(request);
        return hotelMapper.hotelToResponse(hotelRepository.save(newHotel));
    }

    public HotelUpdateResponse updateHotel(Long id, HotelUpdateRequest request){
        Hotel updateHotel = hotelMapper.hotelUpdateToHotel(id, request);
        Hotel excitedHotel = getHotel(id);
        BeanUtils.copyNonNullProperties(updateHotel, excitedHotel);
        return hotelMapper.hotelToUpdateResponse(hotelRepository.save(excitedHotel));
    }

    public void deleteHotel(Long id) {
        getHotel(id);
        hotelRepository.deleteById(id);
    }

    public Hotel getHotel(Long id) {
        return hotelRepository.findById(id).orElseThrow(() ->
                new HotelNotFoundException(MessageFormat.format("Отель с id {0} не найден.", id)));
    }

    public RatingResponse addRating(RatingRequest request) {
        Hotel hotel = getHotel(request.getHotelId());

        double rating = hotel.getRating();
        int numberOfRating = hotel.getMarkCount();
        int newMark = request.getMark();
        double totalRating = rating * numberOfRating;
        totalRating = totalRating - rating + newMark;
        rating = Double.parseDouble(new DecimalFormat("#.#").format(totalRating / numberOfRating).replace(",", "."));
        numberOfRating++;

        hotel.setRating(rating);
        hotel.setMarkCount(numberOfRating);
        hotelRepository.save(hotel);
        return new RatingResponse(hotel.getId(), newMark, rating);
    }

    public HotelListResponse getHotelsWithFilter(HotelFilterRequest filter, int page, int size) {
        return hotelMapper.hotelListToResponseList(hotelRepository.findAll(
                HotelSpecification.findWithFilter(filter), PageRequest.of(page, size)
        ).getContent(), hotelRepository.findAll().size());
    }
}
