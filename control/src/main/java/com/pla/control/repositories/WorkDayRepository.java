package com.pla.control.repositories;

import com.pla.control.models.WorkDay;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pla.control.models.User;

import java.time.LocalDate;
import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Integer> {


	List<WorkDay> findByDayBetween(LocalDate startDate, LocalDate endDate);
    Optional<WorkDay> findByUserAndDay(User user, LocalDate day);
    List<WorkDay> findByUserAndDayBetween(User user, LocalDate start, LocalDate end);
    @Query("SELECT w FROM WorkDay w WHERE w.user = :user AND w.day BETWEEN :startOfMonth AND :endOfMonth")
    List<WorkDay> findByUserAndMonth(@Param("user") User user, 
                                      @Param("startOfMonth") LocalDate startOfMonth, 
                                      @Param("endOfMonth") LocalDate endOfMonth);
    @Query("SELECT w FROM WorkDay w WHERE w.user = :user AND w.day BETWEEN :startOfMonth AND :endOfMonth")
    List<WorkDay> findByUserAndMonthAndYear(@Param("user") User user,
                                             @Param("startOfMonth") LocalDate startOfMonth, 
                                             @Param("endOfMonth") LocalDate endOfMonth);
    List<WorkDay> findByUser(User user);
    
}
