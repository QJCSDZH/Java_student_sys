package org.example.studentsystem.entity;

import lombok.Data;

@Data
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer teacherId;
}
