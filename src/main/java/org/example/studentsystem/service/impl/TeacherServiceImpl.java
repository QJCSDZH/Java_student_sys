package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.common.BusinessException;
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
    public List<Teacher> getTeachersInfoByName(String name) {
        List<Teacher> teachers = teacherMapper.listByName(name);
        for (Teacher teacher : teachers) {
            List<Student> students = studentMapper.listByTeacherId(teacher.getId());
            teacher.setStudents(students);
        }
        return teachers;
    }



    @Override
    public Teacher getTeacherInfoById(Integer id) {
        Teacher teacher = teacherMapper.listById(id);
        List<Student> students = studentMapper.listByTeacherId(teacher.getId());
        teacher.setStudents(students);
        return teacher;
    }



    @Override
    public Boolean insertTeacherInfo(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.listById(teacherDTO.getId());
        if (teacher != null) {
            throw new BusinessException("该教师已存在");
        }
        boolean success = teacherMapper.insertTeacherInfo(teacherDTO) > 0;
        if (!success) {
            throw new BusinessException("教师信息添加失败");
        }
        return true;
    }
}
