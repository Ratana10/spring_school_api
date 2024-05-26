package com.piseth.schoolapi.enrolls;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class EnrollDTO {

    private Long studentId;

    private Long courseId;

    private LocalDateTime enrollDate;

}
