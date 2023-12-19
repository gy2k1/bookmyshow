package com.example.bookmyshow.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDTO {
    public ResponseStatus responseStatus;
    public Long bookingId;
    public int amount;
}
