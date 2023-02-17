package org.training360.sv2jvjbfzarovizsga.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.sv2jvjbfzarovizsga.dto.CreateStudentCommand;
import org.training360.sv2jvjbfzarovizsga.dto.SchoolDto;
import org.training360.sv2jvjbfzarovizsga.exceptions.EntityNotFoundException;
import org.training360.sv2jvjbfzarovizsga.model.School;
import org.training360.sv2jvjbfzarovizsga.model.Student;
import org.training360.sv2jvjbfzarovizsga.repository.SchoolRepository;
import org.training360.sv2jvjbfzarovizsga.repository.StudentRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class StudentService {

    private StudentRepository studentRepository;
    private SchoolRepository schoolRepository;
    private ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, SchoolRepository schoolRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
    }

    public SchoolDto createStudent(Long id, CreateStudentCommand command) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School", id));
        Student student = new Student(command.getName(), command.getDateOfBirth());
        studentRepository.save(student);
        school.addStudent(student);
        return modelMapper.map(school, SchoolDto.class);
    }

    public SchoolDto fireStudent(Long id, Long stdId) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School", id));
        Student student = studentRepository.findById(stdId).orElseThrow(() -> new EntityNotFoundException("Student", stdId));
        if (!school.getStudents().contains(student)) {
            throw new EntityNotFoundException("Student", stdId);
        }
        school.removeStudent(student);
        return modelMapper.map(school, SchoolDto.class);
    }
}
