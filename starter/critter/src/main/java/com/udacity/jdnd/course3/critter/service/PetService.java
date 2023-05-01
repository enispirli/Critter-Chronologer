package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.util.FetchModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PetService {

    private final PetRepository petRepository;

    private final FetchModelUtil fetchModelUtil;

    private final CustomerRepository customerRepository;

    public PetDTO getPet(Long petId) {
        Pet petModel = fetchModelUtil.getPetModel(petId);
        return mapModelToDTO(petModel);
    }

    public List<PetDTO> getPets() {
        return petRepository.findAll()
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public PetDTO savePet(PetDTO petDTO) {

        Customer customerModel = fetchModelUtil.getCustomerModel(petDTO.getOwnerId());
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerModel);
        Pet savedPet = petRepository.save(pet);

        customerModel.getPets().add(savedPet);
        customerRepository.save(customerModel);
        return mapModelToDTO(savedPet);
    }

    public List<PetDTO> getPetsByCustomerId(long customerId) {
        return petRepository.findAllByCustomerId(customerId)
                .stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
    }

    private PetDTO mapModelToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

}
