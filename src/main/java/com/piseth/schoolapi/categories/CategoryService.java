package com.piseth.schoolapi.categories;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category create(Category category);

    Category update(Long id, Category category);

    void delete(Long id);

    Category getById(Long id);

    List<Category> getCategories();

}
