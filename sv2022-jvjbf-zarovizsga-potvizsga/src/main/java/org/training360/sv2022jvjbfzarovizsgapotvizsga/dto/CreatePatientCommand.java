package org.training360.sv2022jvjbfzarovizsgapotvizsga.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientCommand {

    @NotBlank(message = "Patient name cannot be blank!")
    private String name;
    @PastOrPresent(message = "Registration must be in past or present!")
    private LocalDate registrationDate;
    private HospitalWard hospitalWard;
}
