package org.example.studentsystem.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.studentsystem.entity.TeacherEntity;

import java.util.List;

@Data
@AllArgsConstructor // 自动生成构造方法
@NoArgsConstructor
public class TeacherDetailListVO {
    private Integer pageSize;
    private Integer pageNumber;
    private Integer total;
    private List<TeacherEntity> list;
}
