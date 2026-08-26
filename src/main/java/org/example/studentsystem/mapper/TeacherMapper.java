package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.entity.Teacher;

import java.util.List;

@Mapper

public interface TeacherMapper {
    /// 根据姓名查询教师信息
    @Select("""
            SELECT id, name, age, gender
            FROM teacher
            WHERE name = #{name}
            ORDER BY id
            """)
    List<Teacher> listByName(String name);


    /// 根据ID查询教师信息
    @Select("""
            SELECT id, name, age, gender
            FROM teacher
            WHERE id = #{id}
            ORDER BY id
            """)
    Teacher listById(Integer id);


    /// 插入教师信息
    @Insert("""
            INSERT INTO teacher (id, name, age, gender)
            VALUES (#{id}, #{name}, #{age}, #{gender})
            """)
    int insertTeacherInfo(TeacherDTO teacherDTO);
}


