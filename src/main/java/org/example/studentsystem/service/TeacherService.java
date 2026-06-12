package org.example.studentsystem.service;

import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.example.studentsystem.DTO.TeacherAddDTO;
import org.example.studentsystem.DTO.TeacherPageRequestDTO;
import org.example.studentsystem.DTO.TeacherUpdateDTO;
import org.example.studentsystem.VO.TeacherDetailListVO;
import org.example.studentsystem.VO.TeacherDetailVO;
import org.example.studentsystem.entity.TeacherEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface TeacherService {
    TeacherEntity getTeacherInfoById(Integer id);
    TeacherDetailVO getTeacherDetailById(Integer id);
    boolean insertTeacherInfo(TeacherAddDTO teacherAddDTO);
    boolean updateTeacherInfo(TeacherUpdateDTO teacherUpdateDTO);
    boolean updateTeacherInfoWithParam(@RequestParam Integer id,
                                       @RequestParam Integer age,
                                       @RequestParam String name,
                                       @RequestParam Integer years);
    // 查询teacher表总条数
    //Integer getTeacherInfoTotal();
    // 分页查询teacher表list
    TeacherDetailListVO getTeacherByPage(@Param("teacherPageRequestDTO") TeacherPageRequestDTO teacherPageRequestDTO);
}
