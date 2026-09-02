package org.example.studentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.StudentDTO;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/student/")


public class StudentController {

    private final StudentService studentService;

    // http://127.0.0.1:8081/student/info?name=张1000
    @GetMapping("info")
    public PHResult<List<Student>> getStudentsByName(@RequestParam String name) {
        List<Student> students = studentService.getStudentsByName(name);
        return PHResult.success(students);
    }


    // 根据ID查询学生信息
    // http://127.0.0.1:8081/student/infoById?id=1
    @GetMapping("infoById")
    public PHResult<Student> getStudentById(@RequestParam Integer id){
        Student student = studentService.getById(id);
        return PHResult.success(student);
    }

    // 插入学生信息
    // http://127.0.0.1:8081/student/insert
    @PostMapping("insert")
    public PHResult<Boolean> insert(@RequestBody StudentDTO studentDTO) {
        return PHResult.success(studentService.insert(studentDTO));
    }
}
