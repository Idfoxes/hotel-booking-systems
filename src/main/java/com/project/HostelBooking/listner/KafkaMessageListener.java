package com.project.HostelBooking.listner;

import com.project.HostelBooking.model.events.BookingEvent;
import com.project.HostelBooking.model.events.RegisterEvent;
import com.project.HostelBooking.repositories.mongo.BookingEventRepository;
import com.project.HostelBooking.repositories.mongo.RegisterEventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final BookingEventRepository bookingEventRepository;
    private final RegisterEventsRepository registerEventsRepository;

    @KafkaListener(
            topics = "${app.kafka.kafkaMessageRegisterTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listenRegisterEvent(@Payload RegisterEvent registerEvent,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received register event: {}", registerEvent);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);

        registerEventsRepository.save(registerEvent);
    }

    @KafkaListener(
            topics = "${app.kafka.kafkaMessageNewBookingTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listenNewBookingEvent(@Payload BookingEvent bookingEvent,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received new booking event: {}", bookingEvent);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);

        bookingEventRepository.save(bookingEvent);
    }
}
