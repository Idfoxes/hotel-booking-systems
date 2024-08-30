package com.project.HostelBooking.services;

import com.project.HostelBooking.model.events.BookingEvent;
import com.project.HostelBooking.model.events.RegisterEvent;
import com.project.HostelBooking.repositories.mongo.BookingEventRepository;
import com.project.HostelBooking.repositories.mongo.RegisterEventsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RegisterEventsRepository registerEventsRepository;
    private final BookingEventRepository bookingEventRepository;

    public void exportStatisticsToCsv(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("userId\n");
            for (RegisterEvent event : registerEventsRepository.findAll()) {
                writer.append(event.getUserId().toString()).append("\n");
            }

            writer.append("userId;checkInDate;checkOutDate\n");
            for (BookingEvent event : bookingEventRepository.findAll()) {
                writer.append(event.getUserId().toString())
                        .append(";")
                        .append(event.getCheckInDate().toString())
                        .append(";")
                        .append(event.getCheckOutDate().toString())
                        .append("\n");
            }
        }
    }
}
