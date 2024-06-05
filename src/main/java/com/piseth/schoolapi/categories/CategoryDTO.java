package com.piseth.schoolapi.categories;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotNull(message = "name is required")
    private String name;
}
