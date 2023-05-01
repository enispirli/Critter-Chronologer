package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.util.FetchModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final FetchModelUtil fetchModelUtil;

    public List<ScheduleDTO> getSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = fetchModelUtil.getPets(scheduleDTO.getPetIds());
        schedule.setPets(pets);

        List<Employee> employees = fetchModelUtil.getEmployees(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return mapModelToDTO(savedSchedule);
    }

    public List<ScheduleDTO> getSchedulesByPet(Long petId) {

        Pet pet = fetchModelUtil.getPetModel(petId);
        return scheduleRepository.findByPetsContains(pet)
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByEmployee(Long employeeId) {

        Employee employee = fetchModelUtil.getEmployeeModel(employeeId);
        return scheduleRepository.findByEmployeesContains(employee)
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getSchedulesByCustomer(Long customerId) {
        Customer customer = fetchModelUtil.getCustomerModel(customerId);
        return scheduleRepository.findByPetsIn(customer.getPets())
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }


    private ScheduleDTO mapModelToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (!CollectionUtils.isEmpty(schedule.getEmployees())){
            scheduleDTO.setEmployeeIds(new ArrayList<>());
            schedule.getEmployees()
                    .forEach(employee ->
                            scheduleDTO.getEmployeeIds().add(employee.getId())
                    );
        }

        if (!CollectionUtils.isEmpty(schedule.getPets())){
            scheduleDTO.setPetIds(new ArrayList<>());
            schedule.getPets()
                    .forEach(pet ->
                            scheduleDTO.getPetIds().add(pet.getId())
                    );
        }

        return scheduleDTO;
    }
}
