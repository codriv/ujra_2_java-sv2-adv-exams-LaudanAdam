package org.training360.sv2022jvjbfzarovizsgapotvizsga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
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
    public HospitalDto createHospital(@Valid @RequestBody CreateHospitalCommand command) {
        return hospitalService.createHospital(command);
    }

    @PostMapping("/{id}/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@PathVariable("id") Long id, @Valid @RequestBody CreatePatientCommand command) {
        return patientService.createPatient(id, command);
    }

    @GetMapping("/{id}/patients")
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDto> getPatients(@PathVariable("id") Long id, @RequestParam Optional<HospitalWard> hospitalWard) {
        return patientService.getPatients(id, hospitalWard);
    }

    @DeleteMapping("/{id}/patients/{ptId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePatient(@PathVariable("id") Long id, @PathVariable("ptId") Long ptId) {
        patientService.removePatient(id, ptId);
    }
}
