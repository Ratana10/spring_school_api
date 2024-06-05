package com.piseth.schoolapi.courses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class CourseDTO {
    @NotNull(message = "name is required")
    private String name;
    @Null
    private String description;
    @Null
    private String image;
    @NotNull(message = "student price is required")
    private String studentPrice;
    @NotNull(message = "normal price is required")
    private String normalPrice;
    @NotNull(message = "study type is required")
    private Long studyTypeId;
    @NotNull(message = "category is required")
    private Long categoryId;

}
