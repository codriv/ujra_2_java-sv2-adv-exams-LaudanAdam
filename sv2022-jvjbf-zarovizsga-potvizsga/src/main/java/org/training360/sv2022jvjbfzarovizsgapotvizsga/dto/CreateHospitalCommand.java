package org.training360.sv2022jvjbfzarovizsgapotvizsga.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHospitalCommand {

    @NotBlank(message = "Hospital name cannot be blank!")
    private String hospitalName;
}
