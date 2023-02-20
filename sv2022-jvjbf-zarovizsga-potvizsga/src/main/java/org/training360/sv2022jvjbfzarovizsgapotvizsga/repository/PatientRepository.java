package org.training360.sv2022jvjbfzarovizsgapotvizsga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.hospital.id = :id and (:hospitalWard IS NULL OR :hospitalWard = p.hospitalWard) order by p.name")
    List<Patient> getPatients(Long id, Optional<HospitalWard> hospitalWard);
}
