package org.training360.sv2jvjbfzarovizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.sv2jvjbfzarovizsga.dto.CreateSchoolCommand;
import org.training360.sv2jvjbfzarovizsga.dto.SchoolDto;
import org.training360.sv2jvjbfzarovizsga.model.Address;
import org.training360.sv2jvjbfzarovizsga.model.School;
import org.training360.sv2jvjbfzarovizsga.repository.SchoolRepository;
import org.training360.sv2jvjbfzarovizsga.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SchoolService {

    private SchoolRepository schoolRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;

    public SchoolService(SchoolRepository schoolRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public SchoolDto createSchool(CreateSchoolCommand command) {
        Address address = new Address(command.getPostalCode(), command.getCity(), command.getStreet(), command.getHouseNumber());
        School school = new School(command.getSchoolName(), address);
        schoolRepository.save(school);
        return modelMapper.map(school, SchoolDto.class);
    }

    public List<SchoolDto> getSchools(Optional<String> city) {
        List<School> schools = schoolRepository.getSchools(city);
        return schools.stream().map(s -> modelMapper.map(s, SchoolDto.class)).toList();
    }
}
