package com.project.HostelBooking.repositories.jpa;

import com.project.HostelBooking.model.Hotel;
import com.project.HostelBooking.web.dto.hotel.HotelFilterRequest;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> findWithFilter(HotelFilterRequest filter){
        return Specification.where(byHotelId(filter.getHotelId()))
                .and(byHotelName(filter.getHotelName()))
                .and(byAdvertisement(filter.getAdvertisement()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistance(filter.getDistance()))
                .and(byRating(filter.getRating(), filter.getMarkCount()));
    }

    static Specification<Hotel> byHotelId(Long hotelId){
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), hotelId);
        };
    }

    static Specification<Hotel> byHotelName(String hotelName){
        return (root, query, criteriaBuilder) -> {
            if (hotelName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), hotelName);
        };
    }

    static Specification<Hotel> byAdvertisement(String advertisement){
        return (root, query, criteriaBuilder) -> {
            if (advertisement == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("advertisement"), advertisement);
        };
    }

    static Specification<Hotel> byCity(String city){
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address){
        return (root, query, criteriaBuilder) -> {
            if (address == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistance(Integer distance){
        return (root, query, criteriaBuilder) -> {
            if (distance == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("distance"), distance);
        };
    }

    static Specification<Hotel> byRating(Double rating, Integer markCount){
        return (root, query, criteriaBuilder) -> {
            if (rating == null && markCount == null) {
                return null;
            } else if (markCount == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
            } else if (rating == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("markCount"), markCount);
            }
            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("markCount"), markCount)
            );
        };
    }
}
