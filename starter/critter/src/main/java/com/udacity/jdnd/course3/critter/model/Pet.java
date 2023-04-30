package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "pets")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_id"))
    private Customer customer;

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate birthDate;

    private String notes;
}
