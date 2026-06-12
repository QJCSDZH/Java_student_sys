package org.example.studentsystem.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.studentsystem.entity.Student;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListVO {
    private Integer pageSize;
    private Integer pageNumber;
    private Integer total;
    private List<Student> list;
}
