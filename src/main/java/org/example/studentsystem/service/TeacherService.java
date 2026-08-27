package org.example.studentsystem.service;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.DTO.TeacherDetailDTO;
import org.example.studentsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachersInfoByName(String name);
    TeacherDetailDTO getTeacherInfoById(Integer id, int pageNum, int pageSize);

    Boolean insertTeacherInfo(TeacherDTO teacherDTO);
}
