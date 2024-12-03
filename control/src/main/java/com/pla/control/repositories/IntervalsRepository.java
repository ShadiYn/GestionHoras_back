package com.pla.control.repositories;

import com.pla.control.models.Intervals;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervalsRepository extends JpaRepository<Intervals, Integer> {
	
}
