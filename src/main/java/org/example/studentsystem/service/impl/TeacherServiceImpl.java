package org.example.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.example.studentsystem.DTO.TeacherAddDTO;
import org.example.studentsystem.DTO.TeacherNamePageRequestDTO;
import org.example.studentsystem.DTO.TeacherPageRequestDTO;
import org.example.studentsystem.DTO.TeacherUpdateDTO;
import org.example.studentsystem.VO.TeacherDetailListVO;
import org.example.studentsystem.VO.TeacherDetailVO;
import org.example.studentsystem.common.exception.BusinessException;
import org.example.studentsystem.entity.Student;
import org.example.studentsystem.entity.TeacherEntity;
import org.example.studentsystem.mapper.StudentMapper;
import org.example.studentsystem.mapper.TeacherMapper;
import org.example.studentsystem.service.TeacherService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    /*
    public  TeacherServiceImpl(TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }
    */


    @Override

    public TeacherEntity getTeacherInfoById(Integer id) {

        String key = "teacher:" + id;
        String cacheJson = stringRedisTemplate.opsForValue().get(key);
        if (cacheJson != null) {
            System.out.println("命中Redis");
            return objectMapper.readValue(cacheJson, TeacherEntity.class);
        }else{
            System.out.println("==========查询MySQL==========");
            TeacherEntity teacher = teacherMapper.getTeacherById(id);
            String json = objectMapper.writeValueAsString(teacher); // json序列化
            stringRedisTemplate.opsForValue().set(key, json, Duration.ofMinutes(30)); // 30分钟过期
            return teacher;
        }

        // return teacherMapper.getTeacherById(id);
    }


    // Spring 声明式事务控制
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
        int result = teacherMapper.updateTeacherInfo(teacherUpdateDTO);
        if (result == 1) {
            Boolean success = stringRedisTemplate.delete("teacher:" + teacherUpdateDTO.getId());
            if (success) {
                System.out.println("==========Redis删除成功==========");
            }else{
                System.out.println("==========Redis删除失败==========");
            }
        }
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

    // 使用 PageHelper 分页查询教师表
    @Override
    public TeacherDetailListVO getTeacherPageList(TeacherPageRequestDTO teacherPageRequestDTO) {
        PageHelper.startPage(teacherPageRequestDTO.getPageNumber(), teacherPageRequestDTO.getPageSize());
        List<TeacherEntity> teacherEntityList = teacherMapper.listTeachers();
        PageInfo<TeacherEntity> pageInfo = new PageInfo<>(teacherEntityList);

        return new TeacherDetailListVO(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                (int) pageInfo.getTotal(),
                pageInfo.getList()
        );
    }

    @Override
    public TeacherDetailListVO searchTeacherPageList(TeacherNamePageRequestDTO requestDTO) {
        if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
            return new TeacherDetailListVO(
                    requestDTO.getPageSize(),
                    requestDTO.getPageNumber(),
                    0,
                    List.of()
            );
        }

        PageHelper.startPage(requestDTO.getPageNumber(), requestDTO.getPageSize());
        List<TeacherEntity> list = teacherMapper.listTeachersByNameLike(requestDTO.getName().trim());
        PageInfo<TeacherEntity> pageInfo = new PageInfo<>(list);

        return new TeacherDetailListVO(
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                (int) pageInfo.getTotal(),
                pageInfo.getList()
        );
    }

}
