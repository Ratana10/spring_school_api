package com.piseth.schoolapi.categories;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CategoryDTO dto) {
        Category category = CategoryMapper.INSTANCE.toCategory(dto);
        category = categoryService.create(category);

        ApiResponse response = ApiResponse.builder()
                .data(CategoryMapper.INSTANCE.toCategoryDTO(category))
                .message("create category successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        Category category = CategoryMapper.INSTANCE.toCategory(dto);
        category = categoryService.update(id, category);

        ApiResponse response = ApiResponse.builder()
                .data(CategoryMapper.INSTANCE.toCategoryDTO(category))
                .message("update category successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        categoryService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete category successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
       Category byId = categoryService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(CategoryMapper.INSTANCE.toCategoryDTO(byId))
                .message("get category successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<Category> categories = categoryService.getCategories();

        ApiResponse response = ApiResponse.builder()
                .data(categories)
                .message("get categories successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
