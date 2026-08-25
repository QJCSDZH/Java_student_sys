package org.example.studentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.entity.Teacher;
import org.example.studentsystem.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher/")

public class TeacherController {
    private final TeacherService teacherService;

    // http://127.0.0.1:8081/teacher/info?name=教师1001
    @GetMapping("info")
    public PHResult<List<Teacher>> getTeachersByName(@RequestParam String name){
        List<Teacher> teachers = teacherService.getTeachersByName(name);
        return PHResult.success(teachers);
    }
}
