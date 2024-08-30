package com.project.HostelBooking.repositories.jpa;

import com.project.HostelBooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
