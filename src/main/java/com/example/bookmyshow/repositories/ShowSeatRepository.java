package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    Optional<ShowSeat>findAllById(Iterator<Long>ids);

    ShowSeat save(ShowSeat showSeat);
}
