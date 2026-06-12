package org.example.studentsystem.mapper;

import org.apache.ibatis.annotations.*;
import org.example.studentsystem.DTO.TeacherAddDTO;
import org.example.studentsystem.DTO.TeacherUpdateDTO;
import org.example.studentsystem.entity.TeacherEntity;

import java.util.List;

@Mapper
public interface TeacherMapper {
    // 查询数据
    @Select("select * from teacher where id = #{id}")
    public TeacherEntity getTeacherById(int id);

    // 新增数据
    @Insert("""

    insert into teacher( name, age, years)

    values(#{name}, #{age}, #{years})
    
    """)
    public int insertTeacherInfo(TeacherAddDTO teacher);


    // 修改数据
    @Update("""
            update teacher
    
            set
    
            name = #{name},
    
            age = #{age},
    
            years = #{years}
    
            where id = #{id}
    
    """)
    public int updateTeacherInfoWithParam(TeacherEntity teacher);


    // 修改数据
    @Update("""
            update teacher
    
            set
    Ï
            name = #{name},
    
            age = #{age},
    
            years = #{years}Ï
    
            where id = #{id}
    
    """)
    public int updateTeacherInfo(TeacherUpdateDTO teacherUpdateDTO);


    // 通过名字查找
    @Select("""
            select * from teacher where name = #{name}
            """)
    public TeacherEntity getTeacherByName(String name);


    // 分页查询
    @Select("""
            select * from teacher 
            limit #{offset}, #{pageSize}
            """)
    public List<TeacherEntity> getTeacherByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    // 查询teacher表的总条数
    @Select( "select count(*) from teacher" )
    public int getTeacherInfoTotal();
}
