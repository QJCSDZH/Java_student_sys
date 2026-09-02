package org.example.studentsystem.DTO;

import lombok.Data;

@Data
public class StudentDTO {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer teacherId;
}
