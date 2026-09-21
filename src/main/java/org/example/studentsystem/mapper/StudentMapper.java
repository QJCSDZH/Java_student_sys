package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.studentsystem.DTO.StudentDTO;
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

    /// 根据ID查询学生数据
    @Select("""
            SELECT id, name, age, gender, chinese, math, english, teacher_id AS teacherId
            FROM student
            WHERE id = #{id}
            ORDER BY id
            """)
    Student getById(Integer id);

    /// 插入学生数据
    @Insert("""
            INSERT INTO student (id, name, age, gender, chinese, math, english, teacher_id)
            VALUES (#{id}, #{name}, #{age}, #{gender}, #{chinese}, #{math}, #{english}, #{teacherId})
            """)
    int insert(StudentDTO studentDTO);


    /// 根据ID修改学生数据,只修改StudentDTO模型中除ID外的非空非null数据
    @Update("""
            <script>
            UPDATE student
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
                <if test="chinese != null">
                    chinese = #{chinese},
                </if>
                <if test="math != null">
                    math = #{math},
                </if>
                <if test="english != null">
                    english = #{english},
                </if>
                <if test="teacherId != null">
                    teacher_id = #{teacherId},
                </if>
            </set>
            WHERE id = #{id}
            </script>
            """)
    int update(StudentDTO studentDTO);
}
