package com.piseth.schoolapi.enrolls;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class EnrollDTO {

    private Long studentId;

    private List<Long> courseIds;

    @JsonFormat(pattern = "dd-mm-yyyy HH:mm")
    private LocalDateTime enrollDate;
}
