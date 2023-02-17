package org.training360.sv2jvjbfzarovizsga.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.training360.sv2jvjbfzarovizsga.model.Address;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchoolDto {

    private Long id;
    private String schoolName;
    @Embedded
    private Address address;
    private List<StudentDto> students;
}
