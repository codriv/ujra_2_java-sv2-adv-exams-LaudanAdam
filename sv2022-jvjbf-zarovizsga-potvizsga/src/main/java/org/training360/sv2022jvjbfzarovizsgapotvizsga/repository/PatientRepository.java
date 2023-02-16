package org.training360.sv2022jvjbfzarovizsgapotvizsga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.hospital.id = :id and (:ward is null or p.hospitalWard = :ward) order by p.name")
    List<Patient> findPatients(Long id, HospitalWard ward);
}
