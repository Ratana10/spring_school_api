package com.piseth.schoolapi.students;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class StudentDTO {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "student-type is required")
    private String studentType;

    @Null
    private String gender;
}
