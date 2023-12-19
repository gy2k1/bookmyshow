package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.BookMovieRequestDTO;
import com.example.bookmyshow.dto.BookMovieResponseDTO;
import com.example.bookmyshow.dto.ResponseStatus;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class BookingController {

    BookingService bookingService;
    @Autowired
    BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO bookMovieRequestDTO){
        BookMovieResponseDTO bookMovieResponseDTO = new BookMovieResponseDTO();
        try {
            Booking booking= bookingService.bookMovie(
                    bookMovieRequestDTO.getShowSeatIds(),
                    bookMovieRequestDTO.getShowId(),
                    bookMovieRequestDTO.getUserId());
            bookMovieResponseDTO.setBookingId(booking.getId());
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDTO.setAmount(booking.getAmount());
        }
        catch (Exception e){
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookMovieResponseDTO;
    }
}
