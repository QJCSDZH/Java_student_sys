package org.example.studentsystem.service;

import org.example.studentsystem.entity.Student;
import org.example.studentsystem.DTO.StudentDTO;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsByName(String name);
    Student getById(int id);
    Boolean insert(StudentDTO studentDTO);
}
