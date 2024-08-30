package com.app.springsecurity.controller;

import com.app.springsecurity.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>();
    Student s1 = new Student(1, "John", "Java");
    Student s2 = new Student(2, "Doe", "Python");
    Student s3 = new Student(3, "Smith", "C++");

    public StudentController() {
        students.add(s1);
        students.add(s2);
        students.add(s3);
    }

//    @GetMapping("csrf")
//    public CsrfToken csrfToken(HttpServletRequest req) {
//        return (CsrfToken) req.getAttribute(CsrfToken.class.getName());
//    }

    @GetMapping("students")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("students")
    public void addStudent(@RequestBody Student student) {
        students.add(student);
    }
}
