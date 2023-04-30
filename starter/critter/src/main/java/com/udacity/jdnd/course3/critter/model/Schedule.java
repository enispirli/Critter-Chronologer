package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(
            name = "schedule_employee",
            joinColumns = { @JoinColumn(name = "schedule_id")},
            inverseJoinColumns = { @JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = { @JoinColumn(name = "schedule_id")},
            inverseJoinColumns = { @JoinColumn(name = "pet_id")}
    )
    private List<Pet> pets;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;

    @ElementCollection
    @Column(name = "activity")
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> activities;
}
