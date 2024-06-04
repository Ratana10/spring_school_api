package com.piseth.schoolapi.studytypes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudyTypeDTO {
    @NotNull(message = "name is required")
    private String name;
}
