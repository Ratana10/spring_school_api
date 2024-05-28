package com.piseth.schoolapi.promotion;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PromotionsDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> courseIds;
}
