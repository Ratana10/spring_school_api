package com.piseth.schoolapi.promotion;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PromotionDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Long> courseIds;
}
