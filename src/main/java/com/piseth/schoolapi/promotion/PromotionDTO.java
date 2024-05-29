package com.piseth.schoolapi.promotion;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PromotionDTO {
    private String name;
    private String description;
    private Set<Long> promotionCourseIds;
    private Set<Long> requiredCourseIds;
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
