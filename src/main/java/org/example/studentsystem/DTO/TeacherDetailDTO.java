package org.example.studentsystem.DTO;

import lombok.Data;
import org.example.studentsystem.entity.Student;

import java.util.List;

@Data
public class TeacherDetailDTO {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private List<Student> students;
    private long total;
    private int pageNum;
    private int pageSize;
    private int pages;
}
