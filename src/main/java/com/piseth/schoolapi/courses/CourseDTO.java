package com.piseth.schoolapi.courses;

import lombok.Data;

@Data
public class CourseDTO {
    private String name;
    private String description;
    private String image;
    private String studentPrice;
    private String normalPrice;
    private Long studyTypeId;
    private Long categoryId;

}
