package com.udacity.jdnd.course3.critter.util;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchModelUtil {

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    private final ScheduleRepository scheduleRepository;

    public Employee getEmployeeModel(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found for " + employeeId));
    }

    public Customer getCustomerModel(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found for " + customerId));
    }

    public Pet getPetModel(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found for " + petId));
    }


    public List<Employee> getEmployees(List<Long> employeeIdList) {
        return employeeRepository.findAllByIdIn(employeeIdList);
    }

    public List<Pet> getPets(List<Long> petIdList) {
        return petRepository.findAllByIdIn(petIdList);
    }
}
