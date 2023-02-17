package org.training360.sv2jvjbfzarovizsga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolCommand {

    @NotBlank(message = "Schoolname cannot be blank!")
    private String schoolName;
    private String postalCode;
    private String city;
    private String street;
    private int houseNumber;
}
