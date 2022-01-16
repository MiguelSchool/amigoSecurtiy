package com.example.amigosecurtiy.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> students = Arrays.asList(
            new Student(1L,"James Bond"),
            new Student(2L,"Maria Jhones"),
            new Student(3L,"Peter Parker"),
            new Student(4L,"Anna Smith")
    );
    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") Long id ) {
        return students.stream()
                .filter(student -> id.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
