package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions.EntityNotFoundException;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Hospital;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.HospitalRepository;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.PatientRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    private HospitalRepository hospitalRepository;
    private PatientRepository patientRepository;
    private ModelMapper modelMapper;

    public PatientService(HospitalRepository hospitalRepository, PatientRepository patientRepository, ModelMapper modelMapper) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public PatientDto createPatient(Long id, CreatePatientCommand command) {
        Hospital hospital = findHospitalById(id);
        Patient patient = new Patient(command.getName(), command.getRegistrationDate(), command.getHospitalWard());
        patientRepository.save(patient);
        hospital.addPatient(patient);
        return modelMapper.map(patient, PatientDto.class);
    }

    public List<PatientDto> getPatients(Long id, Optional<HospitalWard> hospitalWard) {
        Hospital hospital = findHospitalById(id);
        List<Patient> patients = patientRepository.getPatients(id, hospitalWard);
        return patients.stream().map(p -> modelMapper.map(p, PatientDto.class)).toList();
    }

    private Hospital findHospitalById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hospital", id));
    }

    public void removePatient(Long id, Long ptId) {
        Hospital hospital = findHospitalById(id);
        Patient patient = patientRepository.findById(ptId).orElseThrow(() -> new EntityNotFoundException("Patient", ptId));
        if (!hospital.getPatients().contains(patient)) {
            throw new EntityNotFoundException("Patient", ptId);
        }
        hospital.removePatient(patient);
    }
}
