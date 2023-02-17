package org.training360.sv2jvjbfzarovizsga.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "school_name")
    private String schoolName;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "school")
    private List<Student> students = new ArrayList<>();

    public School(String schoolName, Address address) {
        this.schoolName = schoolName;
        this.address = address;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setSchool(null);
    }
}
