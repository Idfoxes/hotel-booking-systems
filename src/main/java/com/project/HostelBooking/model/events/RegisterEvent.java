package com.project.HostelBooking.model.events;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "register_events")
public class RegisterEvent {

    @Id
    private String id;

    private Long userId;
}
