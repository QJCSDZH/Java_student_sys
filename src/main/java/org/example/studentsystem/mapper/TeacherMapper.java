package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.studentsystem.entity.Teacher;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("""
            SELECT id, name, age, gender
            FROM teacher
            WHERE name = #{name}
            ORDER BY id
            """)
    List<Teacher> listByName(String name);
}
