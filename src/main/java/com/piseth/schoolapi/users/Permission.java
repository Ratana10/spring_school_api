package com.piseth.schoolapi.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    STUDY_TYPE_READ("study_type:read"),
    STUDY_TYPE_WRITE("study_type:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    ;

    @Getter
    private final String permission;
}