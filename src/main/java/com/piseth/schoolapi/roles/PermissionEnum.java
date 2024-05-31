package com.piseth.schoolapi.roles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
    COURSE_WRITE("course:write"),
    COURSE_READ("course:read"),
    CATEGORY_WRITE("category:write"),
    CATEGORY_READ("category:read");
    private String description;

}