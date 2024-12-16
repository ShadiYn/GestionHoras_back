package com.pla.control.repositories;

import com.pla.control.models.Intervals;
import java.util.List;
import com.pla.control.models.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervalsRepository extends JpaRepository<Intervals, Integer> {
    List<Intervals> findByWorkDayIn(List<WorkDay> workDays);

	List<Intervals> findByWorkDay(WorkDay workDay);
   
}
