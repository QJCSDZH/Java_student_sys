package org.example.studentsystem.entity;
import lombok.Data;

import java.util.List;

@Data
public class Teacher {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private List<Student> students;
}
