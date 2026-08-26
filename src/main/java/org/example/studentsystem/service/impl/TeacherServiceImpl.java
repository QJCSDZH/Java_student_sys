package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.entity.Teacher;
import org.example.studentsystem.mapper.StudentMapper;
import org.example.studentsystem.mapper.TeacherMapper;
import org.example.studentsystem.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<Teacher> getTeachersWithStudentsByName(String name) {
        List<Teacher> teachers = teacherMapper.listByName(name);
        for (Teacher teacher : teachers) {
            List<Student> students = studentMapper.listByTeacherId(teacher.getId());
            teacher.setStudents(students);
        }
        return teachers;
    }
}
