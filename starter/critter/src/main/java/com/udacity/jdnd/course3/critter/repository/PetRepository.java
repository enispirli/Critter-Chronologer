package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
