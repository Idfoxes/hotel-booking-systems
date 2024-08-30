package com.project.HostelBooking.repositories.jpa;

import com.project.HostelBooking.model.Room;
import com.project.HostelBooking.model.UnavailableDate;
import com.project.HostelBooking.web.dto.room.RoomFilterRequest;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> findWithFilter(RoomFilterRequest filter){
        return Specification.where(byRoomId(filter.getRoomId()))
                .and(byRoomName(filter.getRoomName()))
                .and(byPrice(filter.getMinCost(), filter.getMaxCost()))
                .and(byMaxPeopleCount(filter.getMaxPeopleCount()))
                .and(byAvailableDates(filter.getCheckInDate(), filter.getCheckOutDate()));
    }

    static Specification<Room> byRoomId(Long roomId){
        return (root, query, criteriaBuilder) -> {
            if (roomId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byRoomName(String roomName){
        return (root, query, criteriaBuilder) -> {
            if (roomName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id"), roomName);
        };
    }

    static Specification<Room> byPrice(Integer minCost, Integer maxCost){
        return (root, query, criteriaBuilder) -> {
            if (minCost == null && maxCost == null) {
                return criteriaBuilder.conjunction();
            }
            if (minCost == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxCost);
            }
            if (maxCost == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minCost);
            }
            return criteriaBuilder.between(root.get("price"), minCost, maxCost);
        };
    }

    static Specification<Room> byMaxPeopleCount(Integer maxPeopleCount){
        return (root, query, criteriaBuilder) -> {
            if (maxPeopleCount == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("maxPeopleCount"), maxPeopleCount);
        };
    }

    static Specification<Room> byAvailableDates(LocalDate checkInDate, LocalDate checkOutDate){
        return (root, query, criteriaBuilder) -> {
            if (checkInDate == null || checkOutDate == null) {
                return criteriaBuilder.conjunction();
            }

            Subquery<UnavailableDate> subquery = query.subquery(UnavailableDate.class);
            Root<UnavailableDate> subRoot = subquery.from(UnavailableDate.class);
            subquery.select(subRoot.get("id"));

            Predicate roomMatch = criteriaBuilder.equal(subRoot.get("room"), root);
            Predicate dateOverlap = criteriaBuilder.between(subRoot.get("unavailableDate"), checkInDate, checkOutDate);

            subquery.where(criteriaBuilder.and(roomMatch, dateOverlap));

            return criteriaBuilder.not(criteriaBuilder.exists(subquery));
        };
    }
}
