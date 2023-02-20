package org.training360.sv2022jvjbfzarovizsgapotvizsga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

    private Long id;
    private String hospitalName;
    private List<PatientDto> patients;
}
