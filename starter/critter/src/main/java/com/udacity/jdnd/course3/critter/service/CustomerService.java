package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll().
                stream().
                map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerByPetId(Long petId) {
        Optional<Pet> petWrapper = petRepository.findById(petId);
        return petWrapper.map(pet -> mapModelToDTO(pet.getCustomer()))
                .orElse(null);
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if (!CollectionUtils.isEmpty(customerDTO.getPetIds())) {
            List<Pet> pets = new ArrayList<>();

            customerDTO.getPetIds().forEach(petId -> {
                Optional<Pet> petWrapper = petRepository.findById(petId);
                if (petWrapper.isPresent()) {
                    pets.add(petWrapper.get());
                }
            });
            customer.setPets(pets);
        }

        Customer savedCustomer = customerRepository.save(customer);
        return mapModelToDTO(savedCustomer);
    }

    private CustomerDTO mapModelToDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(c, dto);
        if (!CollectionUtils.isEmpty(c.getPets())) {
            dto.setPetIds(new ArrayList<>());
            c.getPets().forEach(pet -> {
                dto.getPetIds().add(pet.getId());
            });
        }
        return dto;
    }

}
