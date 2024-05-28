package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
