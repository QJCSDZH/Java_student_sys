package org.example.studentsystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentPageRequestDTO {
    @NotNull
    @Min(value = 1)
    private Integer pageSize = 10;

    @NotNull
    @Min(value = 1)
    private Integer pageNumber = 1;

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }
}
