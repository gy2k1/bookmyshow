package com.example.bookmyshow.services;

import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    UserRepository userRepository;
    ShowRepository showRepository;

    PriceCalculator priceCalculator;
    ShowSeatRepository showSeatRepository;

    BookingRepository bookingRepository;

    @Autowired
    BookingService(UserRepository userRepository,ShowRepository showRepository,ShowSeatRepository showSeatRepository,BookingRepository bookingRepository,PriceCalculator priceCalculator){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculator = priceCalculator;
    }
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public Booking bookMovie(List<Long> showSeatIds,Long showId,Long userId){
        Optional<User>userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("No User Found");
        }
        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("No Show Like that");
        }

        Show bookedShow = showOptional.get();

        List<ShowSeat>showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat: showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            (Duration.between(showSeat.getLockedAt().toInstant(),new Date().toInstant()).toMinutes()> 15))){
                throw new RuntimeException("Selected seats are not available!");
            }
        }

        List<ShowSeat>savedShowSeats = new ArrayList<>();

        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLockedAt(new Date());
            // 7. Save it in the database
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        Booking booking = new Booking();

        booking.setShowSeats(savedShowSeats);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setPayments(new ArrayList<>());
        booking.setAmount(priceCalculator.getCalculatePrice(savedShowSeats,bookedShow));
        booking = bookingRepository.save(booking);
        return booking;
    }
}
