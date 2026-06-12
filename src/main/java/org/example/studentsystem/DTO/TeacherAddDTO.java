package org.example.studentsystem.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TeacherAddDTO {

    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20之间")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 60, message = "年龄不能大于60")
    private Integer age;

    @NotNull(message = "工龄不能为空")
    @PositiveOrZero(message = "工龄不能小于0")
    private Integer years;
}
