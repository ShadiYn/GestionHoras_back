package com.pla.control.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name="intervals")
public class Intervals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_interval;
    private LocalTime start_time;
    private LocalTime end_time;

    @ManyToOne
    @JoinColumn(name = "workday_id", nullable = false)
    private WorkDay workDay;

}
