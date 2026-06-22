package org.example.studentsystem.controller;

import jakarta.validation.Valid;
import org.example.studentsystem.DTO.StudentPageRequestDTO;
import org.example.studentsystem.VO.StudentListVO;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.common.annotation.OperationLog;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.mapper.StudentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentMapper studentMapper;

    public StudentController(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    // region 查询学生

    // http://127.0.0.1:8081/student/1001
    @OperationLog("根据ID查询学生信息")
    @GetMapping("/student/{id}")
    public PHResult<Student> getStudent(@PathVariable Integer id) {
        Student student = studentMapper.getStudentById(id);
        return PHResult.success(student);
    }

    // http://127.0.0.1:8081/student/getStudentList
    @OperationLog("分页查询学生列表")
    @PostMapping("/student/getStudentList")
    public PHResult<StudentListVO> getStudentList(@Valid @RequestBody StudentPageRequestDTO studentPageRequestDTO) {
        Integer total = studentMapper.getStudentTotal();
        List<Student> studentList = studentMapper.getStudentByPage(
                studentPageRequestDTO.getOffset(),
                studentPageRequestDTO.getPageSize()
        );
        StudentListVO studentListVO = new StudentListVO(
                studentPageRequestDTO.getPageSize(),
                studentPageRequestDTO.getPageNumber(),
                total,
                studentList
        );
        return PHResult.success(studentListVO);
    }

    //endregion
}
