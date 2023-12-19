package com.example.bookmyshow.dto;

import com.example.bookmyshow.models.ShowSeat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookMovieRequestDTO {
    public List<Long>showSeatIds;
    public Long userId;
    public Long showId;

}
