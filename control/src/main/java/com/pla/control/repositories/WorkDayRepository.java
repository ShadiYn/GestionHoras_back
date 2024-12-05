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

	List<WorkDay> findByUser(User user);

	List<WorkDay> findByDayBetween(LocalDate startDate, LocalDate endDate);
    Optional<WorkDay> findByUserAndDay(User user, LocalDate day);

}
