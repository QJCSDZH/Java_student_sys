package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    /// 更新教师数据
    // 要求:先检查是否有该教师ID,有就更新传入的非空字段
    @Update("""
            <script>
            UPDATE teacher
            <set>
                <if test="name != null and name != ''">
                    name = #{name},
                </if>
                <if test="age != null">
                    age = #{age},
                </if>
                <if test="gender != null and gender != ''">
                    gender = #{gender},
                </if>
            </set>
            WHERE id = #{id}
            </script>
            """)
    int updateTeacherInfo(TeacherDTO teacherDTO);
}


