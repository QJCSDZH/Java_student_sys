package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.mapper.StudentMapper;
import org.example.studentsystem.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentMapper.listByName(name);
    }
}
