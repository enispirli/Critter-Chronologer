package com.udacity.jdnd.course3.critter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String notes;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

}
