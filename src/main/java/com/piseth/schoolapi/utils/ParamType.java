package com.piseth.schoolapi.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ParamType {
    ID("id"),
    NAME("name");
    private String description;
}
