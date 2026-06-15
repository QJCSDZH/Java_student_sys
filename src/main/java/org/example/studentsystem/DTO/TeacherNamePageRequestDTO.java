package org.example.studentsystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherNamePageRequestDTO {
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer pageSize = 10;

    @NotNull
    @Min(value = 1)
    private Integer pageNumber = 1;
}
