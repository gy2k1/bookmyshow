package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Theatre extends BaseModel{
    @ManyToOne
    private Region region;
    private String name;
    @OneToMany
    List<Screen>screens;
}
