package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.util.FetchModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final FetchModelUtil fetchModelUtil;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapModelToDTO(savedEmployee);
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Employee employee = fetchModelUtil.getEmployeeModel(employeeId);
        return mapModelToDTO(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = fetchModelUtil.getEmployeeModel(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findAvailableEmployees(EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> employees = employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, dayOfWeek);

        List<Employee> result = new ArrayList<>();
        employees.stream().forEach(employee -> {
            if(employee.getSkills().containsAll(skills)){
                result.add(employee);
            }
        });
        return result.stream().
                map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO mapModelToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }


}
