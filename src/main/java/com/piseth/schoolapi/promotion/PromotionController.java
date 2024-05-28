package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseDTO;
import com.piseth.schoolapi.courses.CourseMapper;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final PromotionMapper promotionMapper;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PromotionDTO promotionDTO) {

        Promotion promotion = promotionMapper.toPromotion(promotionDTO, courseService);

        promotion = promotionService.create(promotion);

        ApiResponse response = ApiResponse.builder()
                .data(promotionMapper.toPromotionDTO(promotion))
                .message("create promotion successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody PromotionDTO promotionDTO) {
//        Promotion promotion = promotionMapper.toPromotion(promotionDTO, courseService);
//        promotion = promotionService.update(id, promotion);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("fix update promotion successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        promotionService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete promotion successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPromotions() {
        List<Promotion> promotions = promotionService.getPromotions();

        ApiResponse response = ApiResponse.builder()
                .data(promotions)
                .message("get all promotions successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
