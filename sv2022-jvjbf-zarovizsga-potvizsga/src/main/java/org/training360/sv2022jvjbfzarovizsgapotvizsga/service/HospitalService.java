package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.exceptions.EntityNotFound;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Hospital;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.HospitalRepository;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.PatientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HospitalService {

    private HospitalRepository hospitalRepository;
    private PatientRepository patientRepository;
    private ModelMapper modelMapper;

    public HospitalService(HospitalRepository hospitalRepository, PatientRepository patientRepository, ModelMapper modelMapper) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public HospitalDto createHospital(CreateHospitalCommand command) {
        Hospital hospital = new Hospital(command.getHospitalName());
        hospitalRepository.save(hospital);
        return modelMapper.map(hospital, HospitalDto.class);
    }

    public HospitalDto patientOff(Long hospitalId, Long patientId) {
        Hospital hospital = findHospitalById(hospitalId);
        Patient patient = findPatientById(patientId);
        if (!hospital.getPatients().contains(patient)) {
            throw new EntityNotFound("Patient", patientId);
        }
        hospital.removePatient(patient);
        patient.setHospital(null);
        return modelMapper.map(hospital, HospitalDto.class);
    }

    Hospital findHospitalById(long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new EntityNotFound("Hospital", id));
    }

    private Patient findPatientById(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFound("Patient", id));
    }
}