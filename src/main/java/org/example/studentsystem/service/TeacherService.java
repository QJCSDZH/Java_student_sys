package org.example.studentsystem.service;
import org.example.studentsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getTeachersWithStudentsByName(String name);
}
