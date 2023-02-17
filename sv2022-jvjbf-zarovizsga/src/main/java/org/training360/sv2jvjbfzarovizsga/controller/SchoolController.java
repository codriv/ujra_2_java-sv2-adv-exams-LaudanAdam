package org.training360.sv2jvjbfzarovizsga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.sv2jvjbfzarovizsga.dto.*;
import org.training360.sv2jvjbfzarovizsga.service.SchoolService;
import org.training360.sv2jvjbfzarovizsga.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private SchoolService schoolService;
    private StudentService studentService;

    public SchoolController(SchoolService schoolService, StudentService studentService) {
        this.schoolService = schoolService;
        this.studentService = studentService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto createSchool(@Valid @RequestBody CreateSchoolCommand command) {
        return schoolService.createSchool(command);
    }

    @PostMapping("/{id}/students")
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto createStudent(@PathVariable("id") Long id, @Valid @RequestBody CreateStudentCommand command) {
        return studentService.createStudent(id, command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SchoolDto> getSchools(@RequestParam Optional<String> city) {
        return schoolService.getSchools(city);
    }

    @PutMapping("/{id}/students/{stdId}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolDto fireStudent(@PathVariable("id") Long id, @PathVariable("stdId") Long stdId) {
        return studentService.fireStudent(id, stdId);
    }
}
