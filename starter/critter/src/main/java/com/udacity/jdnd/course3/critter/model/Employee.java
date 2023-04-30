package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ElementCollection
    @Column(name = "skill")
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<DayOfWeek> daysAvailable;
}
