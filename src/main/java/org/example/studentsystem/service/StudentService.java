package org.example.studentsystem.service;

import org.example.studentsystem.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsByName(String name);
}
