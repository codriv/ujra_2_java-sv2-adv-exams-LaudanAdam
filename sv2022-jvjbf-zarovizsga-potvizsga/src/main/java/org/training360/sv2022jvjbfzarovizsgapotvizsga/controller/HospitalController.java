package org.training360.sv2022jvjbfzarovizsgapotvizsga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.service.HospitalService;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.service.PatientService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private HospitalService hospitalService;
    private PatientService patientService;

    public HospitalController(HospitalService hospitalService, PatientService patientService) {
        this.hospitalService = hospitalService;
        this.patientService = patientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private HospitalDto createHospital(@RequestBody @Valid CreateHospitalCommand command) {
        return hospitalService.createHospital(command);
    }

    @PostMapping("{id}/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private PatientDto addPatientToHospital(@PathVariable("id") Long id, @RequestBody @Valid CreatePatientCommand command) {
        return patientService.addPatientToHospital(id, command);
    }

    @GetMapping("{id}/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private List<PatientDto> findPatientsByHospitalAndWardAnd(@PathVariable("id") Long id, Optional<String>hospitalWard) {
        return patientService.findPatientsByHospitalAndWard(id, hospitalWard);
    }

    @DeleteMapping("{id}/patients/{ptId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private HospitalDto patientOff(@PathVariable("id") Long hospitalId, @PathVariable("ptId") Long patientId) {
        return hospitalService.patientOff(hospitalId, patientId);
    }
}
