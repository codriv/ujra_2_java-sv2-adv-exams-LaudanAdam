package org.training360.sv2022jvjbfzarovizsgapotvizsga.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "hospital_ward")
    private HospitalWard hospitalWard;
    @ManyToOne
    private Hospital hospital;

    public Patient(String name, LocalDate registrationDate, HospitalWard hospitalWard) {
        this.name = name;
        this.registrationDate = registrationDate;
        this.hospitalWard = hospitalWard;
    }
}
