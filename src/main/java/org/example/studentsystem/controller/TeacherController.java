package org.example.studentsystem.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.example.studentsystem.DTO.TeacherAddDTO;
import org.example.studentsystem.DTO.TeacherUpdateDTO;
import org.example.studentsystem.DTO.TeacherPageRequestDTO;
import org.example.studentsystem.VO.TeacherDetailListVO;
import org.example.studentsystem.VO.TeacherDetailVO;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.common.annotation.OperationLog;
import org.example.studentsystem.common.context.BaseContext;
import org.example.studentsystem.common.exception.BusinessException;
import org.example.studentsystem.entity.TeacherEntity;
import org.example.studentsystem.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher/")// 添加公共的域名
public class TeacherController {
    private final TeacherService teacherService;

    /*
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    */

    // http://127.0.0.1:8081/teacher/info/1001
    @OperationLog("通过ID查询教师信息")
    @GetMapping("info/{id}")
    public PHResult<TeacherEntity> getTeacher(@PathVariable Integer id) {
        TeacherEntity teacherEntity = teacherService.getTeacherInfoById(id);
        return PHResult.success(teacherEntity);
    }

    // http://127.0.0.1:8081/teacher/insertTeacherInfo
    @OperationLog("新增教师信息")
    @PostMapping("insertTeacherInfo")
    public PHResult<Integer> setTeacher(@Valid @RequestBody TeacherAddDTO teacherAddDTO) {
        boolean result = teacherService.insertTeacherInfo(teacherAddDTO);
        if (result) {
            return PHResult.success(null);
        } else {
            return PHResult.fail("请求失败");
        }
    }

    // json模式
    // http://127.0.0.1:8081/teacher/updateTeacherData
    @OperationLog("修改教师信息")
    @PostMapping("updateTeacherData")
    public PHResult<Integer> updateTeacherData(@RequestBody TeacherUpdateDTO teacherUpdateDTO) {
        boolean resoult = teacherService.updateTeacherInfo(teacherUpdateDTO);
        if (resoult) {
            return PHResult.success(null);
        }else{
            return PHResult.fail("更新失败");
        }

    }


    // 表单模式
    // http://127.0.0.1:8081/teacher/updateTeacherInfo
    @OperationLog("通过表单模式修改教师信息")
    @PostMapping("updateTeacherInfo")
    public PHResult<Integer> updateTeacherInfo(@RequestParam Integer id,
                                     @RequestParam Integer age,
                                     @RequestParam String name,
                                     @RequestParam Integer years
    ) {
        boolean resoult = teacherService.updateTeacherInfoWithParam(id, age, name, years);
        if (resoult) {
            return PHResult.success(null);
        }else{
            return PHResult.fail("更新失败");
        }

    }


    // http://127.0.0.1:8081/teacher/teacherDetail/1001
    @GetMapping("/teacherDetail/{id}")
    public PHResult<TeacherDetailVO> getTeacherDetail(@PathVariable Integer id) {

        TeacherDetailVO result = teacherService.getTeacherDetailById(id);
        if (result == null) {
            return PHResult.fail("请求失败");
        }
        return PHResult.success(result);
    }


    // 获取教师list
    // http://127.0.0.1:8081/teacher/getTeacherList
    @OperationLog("分页查询教师列表")
    @PostMapping("getTeacherList")
    public PHResult<TeacherDetailListVO> getTeacherList(@Valid @RequestBody TeacherPageRequestDTO teacherListDTO) {

        TeacherDetailListVO teacherDetailListVO = teacherService.getTeacherByPage(teacherListDTO);

        System.out.println("========================userId=================================");
        Long userId_baseContext = BaseContext.getUserId();
        System.out.println("baseContext用法:" + userId_baseContext);

        return PHResult.success(teacherDetailListVO);
    }
}
