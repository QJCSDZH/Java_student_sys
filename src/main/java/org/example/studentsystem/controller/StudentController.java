package org.example.studentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/student/")


public class StudentController {

    private final StudentService studentService;

    // http://127.0.0.1:8081/student/info?name=张1000
    @GetMapping("info")
    public PHResult<List<Student>> getStudentsByName(@RequestParam String name) {
        List<Student> students = studentService.getStudentsByName(name);
        return PHResult.success(students);
    }
}
