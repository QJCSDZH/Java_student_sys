package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.studentsystem.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("""
            SELECT id, name, age, gender, chinese, math, english, teacher_id AS teacherId
            FROM student
            WHERE name = #{name}
            ORDER BY id
            """)
    List<Student> listByName(String name);

    @Select("""
            SELECT id, name, age, gender, chinese, math, english, teacher_id AS teacherId
            FROM student
            WHERE teacher_id = #{teacherId}
            ORDER BY id
            """)
    List<Student> listByTeacherId(Integer teacherId);
}
