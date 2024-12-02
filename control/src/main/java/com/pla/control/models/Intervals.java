package com.pla.control.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
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

    public int getId_interval() {
        return id_interval;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
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
