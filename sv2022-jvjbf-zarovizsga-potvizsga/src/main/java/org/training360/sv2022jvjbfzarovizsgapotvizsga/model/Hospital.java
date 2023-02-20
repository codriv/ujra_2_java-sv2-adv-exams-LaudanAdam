package org.training360.sv2022jvjbfzarovizsgapotvizsga.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hospital_name")
    private String hospitalName;
    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients = new ArrayList<>();

    public Hospital(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        patient.setHospital(this);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
        patient.setHospital(null);
    }
}
