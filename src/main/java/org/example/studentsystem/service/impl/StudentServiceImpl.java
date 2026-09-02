package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.StudentDTO;
import org.example.studentsystem.common.BusinessException;
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

    @Override
    public Student getById(int id){
        return studentMapper.getById(id);
    };

    @Override
    public Boolean insert(StudentDTO studentDTO) {
        Student student = studentMapper.getById(studentDTO.getId());
        if (student != null) {
            throw new BusinessException("已存在该学生");
        }
        return studentMapper.insert(studentDTO) > 0;
    }
}
