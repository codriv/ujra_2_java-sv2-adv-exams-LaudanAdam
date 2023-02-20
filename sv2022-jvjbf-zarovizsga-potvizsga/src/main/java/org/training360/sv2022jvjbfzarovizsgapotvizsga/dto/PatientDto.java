package org.training360.sv2022jvjbfzarovizsgapotvizsga.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id;
    private String name;
    private HospitalWard hospitalWard;
}
