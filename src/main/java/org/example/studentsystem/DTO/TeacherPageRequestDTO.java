package org.example.studentsystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TeacherPageRequestDTO {
    @NotNull
    @Min(value = 1)
    private Integer pageSize = 1;

    @NotNull
    @Min(value = 1)
    private Integer pageNumber = 1;

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}
