package com.piseth.schoolapi.categories;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category byId = getById(id);

        byId.setName(category.getName());
        categoryRepository.save(byId);
        return byId;


    }

    @Override
    public void delete(Long id) {
        Category byId = getById(id);

        categoryRepository.deleteById(id);

    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
