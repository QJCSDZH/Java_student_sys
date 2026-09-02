package org.example.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.TeacherDTO;
import org.example.studentsystem.DTO.TeacherDetailDTO;
import org.example.studentsystem.common.BusinessException;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.entity.Teacher;
import org.example.studentsystem.mapper.StudentMapper;
import org.example.studentsystem.mapper.TeacherMapper;
import org.example.studentsystem.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<Teacher> getTeachersInfoByName(String name) {
        List<Teacher> teachers = teacherMapper.listByName(name);
        for (Teacher teacher : teachers) {
            List<Student> students = studentMapper.listByTeacherId(teacher.getId());
            teacher.setStudents(students);
        }
        return teachers;
    }

    @Override
    public TeacherDetailDTO getTeacherInfoById(Integer id, int pageNum, int pageSize) {
        Teacher teacher = teacherMapper.listById(id);
        if (teacher == null) {
            throw new BusinessException("教师不存在");
        }

        PageHelper.startPage(pageNum, pageSize);
        try {
            List<Student> students = studentMapper.listByTeacherId(teacher.getId());
            PageInfo<Student> pageInfo = new PageInfo<>(students);

            TeacherDetailDTO dto = new TeacherDetailDTO();
            dto.setId(teacher.getId());
            dto.setName(teacher.getName());
            dto.setGender(teacher.getGender());
            dto.setAge(teacher.getAge());
            dto.setStudents(pageInfo.getList());
            dto.setTotal(pageInfo.getTotal());
            dto.setPageNum(pageInfo.getPageNum());
            dto.setPageSize(pageInfo.getPageSize());
            dto.setPages(pageInfo.getPages());
            return dto;
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public Boolean insertTeacherInfo(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.listById(teacherDTO.getId());
        if (teacher != null) {
            throw new BusinessException("该教师已存在");
        }
        boolean success = teacherMapper.insertTeacherInfo(teacherDTO) > 0;
        if (!success) {
            throw new BusinessException("教师信息添加失败");
        }
        return true;
    }


    @Override
    public Boolean updateTeacherInfo(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.listById(teacherDTO.getId());
        if (teacher == null) {
            throw new BusinessException("不存在该教师");
        }
        boolean success = teacherMapper.updateTeacherInfo(teacherDTO) > 0;
        if (!success) {
            throw new RuntimeException("教师信息更新失败");
        }
        return true;
    }
}
