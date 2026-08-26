package org.example.studentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.entity.Teacher;
import org.example.studentsystem.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher/")

public class TeacherController {
    private final TeacherService teacherService;

    // 联表查询教师及学生信息
    // http://127.0.0.1:8081/teacher/info?name=教师1001
    @GetMapping("info")
    public PHResult<List<Teacher>> getTeachersByName(@RequestParam String name) {
        return PHResult.success(teacherService.getTeachersInfoByName(name));
    }

    // 插入教师信息
    // http://127.0.0.1:8081/teacher/insert
    @PostMapping("insert")
    public PHResult<Boolean> insertTeacher(@RequestBody TeacherDTO teacherDTO) {

        List<Teacher> teachers = teacherService.getTeachersInfoById(teacherDTO.getId());
        if (!teachers.isEmpty()) {
            return  PHResult.fail("该教师已存在");
        }

        return PHResult.success(teacherService.insertTeacherInfo(teacherDTO));
    }

}
