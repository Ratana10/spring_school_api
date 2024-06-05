package com.piseth.schoolapi.service;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.categories.CategoryRepository;
import com.piseth.schoolapi.categories.CategoryServiceImpl;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;
    private Category category;

    @BeforeEach
    public void init(){
        category = new Category();
        category.setId(1L);
        category.setName("Frontend");
    }


    @Test
    public void createTest(){

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        Category savedCategory = categoryService.create(category);
        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    void updateTest(){

        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category));

        Category savedCategory = categoryService.update(1L, category);
        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    void deleteTest(){

        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category));

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).deleteById(1L);

    }

    @Test
    void deleteNotFoundTest(){

        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> categoryService.delete(1L));

    }

    @Test
    public void getByIdTest() {
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category));

        Category byId = categoryService.getById(1L);

        Assertions.assertThat(byId).isNotNull();

    }

    @Test
    public void getByIdNotFoundTest() {
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> categoryService.getById(1L));
        verify(categoryRepository, times(1)).findById(any(Long.class));

    }

    @Test
    public void getCategoriesTest() {
        Category category1 = new Category();
        category1.setId(2L);
        category1.setName("Backend");

        List<Category> list = Arrays.asList(category, category1);
        when(categoryRepository.findAll())
                .thenReturn(list);

        List<Category> categories = categoryService.getCategories();

        assertEquals(list, categories);

    }
}
