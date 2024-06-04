package com.piseth.schoolapi.promotion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PromotionDTO {
    @NotNull(message = "name is required")
    private String name;


    private String description;


    private Set<Long> promotionCourseIds;

    @NotNull(message = "required courses is required")
    private Set<Long> requiredCourseIds;


    private BigDecimal discountAmount;


    private BigDecimal discountPercentage;

    @NotNull(message = "start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "end date is required")
    private LocalDateTime endDate;

}
