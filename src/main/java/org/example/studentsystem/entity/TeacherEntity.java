package org.example.studentsystem.entity;

import lombok.Data;

@Data
public class TeacherEntity {
    private Integer id;
    private String name;
    private Integer age;
    private Integer years;

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
    public void setYears(Integer years) {this.years = years;}
}
