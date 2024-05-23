package com.piseth.schoolapi.categories;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Category> byId = getById(id);
        if (byId.isPresent()) {
            byId.get().setName(category.getName());
            categoryRepository.save(byId.get());
            return byId.get();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> byId = getById(id);
        if (byId.isPresent()) {
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Category> getById(Long id) {
        return Optional.ofNullable(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id)));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
