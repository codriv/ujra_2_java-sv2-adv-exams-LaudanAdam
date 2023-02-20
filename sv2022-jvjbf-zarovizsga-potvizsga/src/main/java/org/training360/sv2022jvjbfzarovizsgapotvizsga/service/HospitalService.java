package org.training360.sv2022jvjbfzarovizsgapotvizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.Hospital;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.HospitalRepository;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.repository.PatientRepository;

import javax.transaction.Transactional;

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
}
