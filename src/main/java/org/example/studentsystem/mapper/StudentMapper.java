package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.studentsystem.entity.Student;

import java.util.List;

@Mapper

public interface StudentMapper {

    @Select("select * from student where id = #{id}")

    Student getStudentById(Integer id);


    @Select("""

            select * from student

            where
            (#{id} = 1001 and id in (1001,1002))
            or
            (#{id} = 1002 and id = 1002)
                   
            """)
    List<Student> getStudentList(Integer id);

    @Select("""
            select * from student
            limit #{offset}, #{pageSize}
            """)
    List<Student> getStudentByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("select count(*) from student")
    int getStudentTotal();
}
