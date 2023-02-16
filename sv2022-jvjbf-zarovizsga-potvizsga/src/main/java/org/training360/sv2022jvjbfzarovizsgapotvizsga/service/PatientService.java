package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Hospital;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Patient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.PatientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    private PatientRepository patientRepository;
    private ModelMapper modelMapper;
    private HospitalService hospitalService;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper, HospitalService hospitalService) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
        this.hospitalService = hospitalService;
    }

    public PatientDto addPatientToHospital(Long id, CreatePatientCommand command) {
        Patient patient = new Patient(command.getName(), command.getRegistrationDate(), command.getHospitalWard());
        patientRepository.save(patient);
        Hospital hospital = hospitalService.findHospitalById(id);
        hospital.addPatient(patient);
        return modelMapper.map(patient, PatientDto.class);
    }

    public List<PatientDto> findPatientsByHospitalAndWard(Long id, Optional<String> hospitalWard) {
        Hospital hospital = hospitalService.findHospitalById(id);
        HospitalWard ward = getCorrectedWard(hospitalWard);
        List<Patient> patients = patientRepository.findPatients(hospital.getId(), ward);
        return patients.stream().map(p -> modelMapper.map(p, PatientDto.class)).toList();
    }

    private HospitalWard getCorrectedWard(Optional<String> hospitalWard) {
        HospitalWard correctedWard;
        if (hospitalWard.isEmpty() || !Arrays.asList(HospitalWard.values()).stream().map(HospitalWard::toString).toList().contains(hospitalWard.get().toUpperCase())) {
            correctedWard = null;
        } else {
            correctedWard = HospitalWard.valueOf(hospitalWard.get().toUpperCase());
        }
        return correctedWard;
    }
}
