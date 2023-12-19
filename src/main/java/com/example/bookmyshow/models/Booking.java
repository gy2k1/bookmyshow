package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @ManyToMany
    private List<ShowSeat> showSeats;
    @ManyToOne
    User user;
    @ManyToOne
    Show show;
    public Date bookedAt;
    private int amount;
    @OneToMany
    private List<Payment> payments;
}
