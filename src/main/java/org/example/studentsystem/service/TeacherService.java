package org.example.studentsystem.service;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachersInfoByName(String name);
    List<Teacher> getTeachersInfoById(Integer id);

    Boolean insertTeacherInfo(TeacherDTO teacherDTO);
}
