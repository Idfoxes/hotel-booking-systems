package com.project.HostelBooking.exceptions;

public class UnavailableDateException extends RuntimeException{

    public UnavailableDateException(String message) {
        super(message);
    }
}
