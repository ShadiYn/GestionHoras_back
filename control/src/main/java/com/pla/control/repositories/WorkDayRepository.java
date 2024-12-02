package com.pla.control.repositories;

import com.pla.control.models.WorkDay;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pla.control.models.User;
import java.util.List;


public interface WorkDayRepository extends JpaRepository<WorkDay, Integer> {

	
	List<WorkDay> findByUser(User user);
}
