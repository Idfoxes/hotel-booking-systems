package com.project.HostelBooking.repositories.mongo;

import com.project.HostelBooking.model.events.BookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingEventRepository extends MongoRepository<BookingEvent, String> {
}
