package org.example.studentsystem.VO;

import org.example.studentsystem.entity.Student;

import java.util.List;

public class TeacherDetailVO {
    private Integer id;
    private String name;
    private Integer age;
    private Integer years;
    private List<Student> students;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getYears() {
        return years;
    }
    public void setYears(Integer years) {
        this.years = years;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
