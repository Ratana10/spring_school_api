package com.piseth.schoolapi.enrolls;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class EnrollsDTO {

    private Long studentId;

    private List<Long> courseIds;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDate;
}