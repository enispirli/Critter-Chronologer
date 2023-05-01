package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllBySkillsInAndDaysAvailableContains(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);

    List<Employee> findAllByIdIn(List<Long> employeeIdList);
}
