package org.example.studentsystem.service.impl;

import lombok.AllArgsConstructor;
import org.example.studentsystem.DTO.TeacherAddDTO;
import org.example.studentsystem.DTO.TeacherPageRequestDTO;
import org.example.studentsystem.DTO.TeacherUpdateDTO;
import org.example.studentsystem.VO.TeacherDetailListVO;
import org.example.studentsystem.VO.TeacherDetailVO;
import org.example.studentsystem.common.context.BaseContext;
import org.example.studentsystem.common.exception.BusinessException;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.entity.TeacherEntity;
import org.example.studentsystem.mapper.StudentMapper;
import org.example.studentsystem.mapper.TeacherMapper;
import org.example.studentsystem.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    final TeacherMapper teacherMapper;
    final StudentMapper studentMapper;

    /*
    public  TeacherServiceImpl(TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }
    */


    @Override

    public TeacherEntity getTeacherInfoById(Integer id) {
        return teacherMapper.getTeacherById(id);
    }


    // 事务控制
    @Transactional(rollbackFor = Exception.class)
    public boolean insertTeacherInfo(TeacherAddDTO teacherAddDTO) {

        String name = teacherAddDTO.getName();
        TeacherEntity teacherEntity = teacherMapper.getTeacherByName(name);
        if (teacherEntity != null) {
            throw new BusinessException("已存在该教师的名字");
        }

        Integer resout = teacherMapper.insertTeacherInfo(teacherAddDTO);

       // int a = 1 / 0;

        return  resout == 1;
    }


    public boolean updateTeacherInfo(TeacherUpdateDTO teacherUpdateDTO) {
        Integer result = teacherMapper.updateTeacherInfo(teacherUpdateDTO);
        return result == 1;
    }

    @Override
    public boolean updateTeacherInfoWithParam(Integer id, Integer age, String name, Integer years) {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setId(id);
        teacherEntity.setAge(age);
        teacherEntity.setName(name);
        teacherEntity.setYears(years);

        Integer result = teacherMapper.updateTeacherInfoWithParam(teacherEntity);
        return result == 1;
    }


    public TeacherDetailVO getTeacherDetailById(Integer id) {
        if (id != 1001 && id != 1002) {
            return null;
        }
        TeacherEntity teacherEntity = teacherMapper.getTeacherById(id);
        if (teacherEntity == null) {throw new BusinessException("教师不存在");}

        List<Student> students = studentMapper.getStudentList(id);
        if (students == null || students.isEmpty()) {
            throw new BusinessException("学生表查询失败");
        }
        TeacherDetailVO teacherDetailVO = new TeacherDetailVO();
        teacherDetailVO.setId(id);
        teacherDetailVO.setName(teacherEntity.getName());
        teacherDetailVO.setAge(teacherEntity.getAge());
        teacherDetailVO.setYears(teacherEntity.getYears());
        teacherDetailVO.setStudents(students);
        return teacherDetailVO;
    }

    // 查询教师表总条数
//    public Integer getTeacherInfoTotal() {
//        return teacherMapper.getTeacherInfoTotal();
//    }

    // 分页查询教师表list
    public TeacherDetailListVO getTeacherByPage(TeacherPageRequestDTO teacherPageRequestDTO) {

        Integer total = teacherMapper.getTeacherInfoTotal();

        List<TeacherEntity> teacherEntityList = teacherMapper.getTeacherByPage(teacherPageRequestDTO.getOffset(), teacherPageRequestDTO.getPageSize());

        System.out.println("========================sever中获取userId=================================");
        Long userId_baseContext = BaseContext.getUserId();
        System.out.println("baseContext用法:" + userId_baseContext);

        return  new TeacherDetailListVO(teacherPageRequestDTO.getPageSize(),  teacherPageRequestDTO.getPageNumber(), total, teacherEntityList);
    }

}
