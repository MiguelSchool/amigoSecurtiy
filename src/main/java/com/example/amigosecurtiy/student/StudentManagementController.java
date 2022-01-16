package com.example.amigosecurtiy.student;

import lombok.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Getter
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> students = Arrays.asList(
            new Student(1L,"James Bond"),
            new Student(2L,"Maria Jhones"),
            new Student(3L,"Peter Parker"),
            new Student(4L,"Anna Smith")
    );

    // hasRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')
    @GetMapping
    @PreAuthorize("hasAuthority('student:read')")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        //students.add(student);
        System.out.println("register"+ student);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Long id) {
        System.out.println("delete "+id);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Long id, Student student) {
        System.out.println(id+ " student: " +student);
    }
}
