package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.entity.Teacher;
import org.example.studentsystem.mapper.TeacherMapper;
import org.example.studentsystem.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TeacherServiceImpl implements TeacherService {
    private  final TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getTeachersByName(String name) {
        return teacherMapper.listByName(name);
    }

}
