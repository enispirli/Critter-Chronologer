package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
