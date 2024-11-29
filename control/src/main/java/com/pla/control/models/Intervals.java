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


    public int getId_interval() {
        return id_interval;
    }

    public void setId_interval(int id_interval) {
        this.id_interval = id_interval;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }
}
