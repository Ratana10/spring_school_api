package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.exception.ApiResponse;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PromotionDTO promotionDTO) {

        Promotion promotion = promotionMapper.toPromotion(promotionDTO);

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
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid  @RequestBody PromotionDTO promotionDTO) {
        Promotion promotion = promotionMapper.toPromotion(promotionDTO);
        promotion = promotionService.update(id, promotion);

        ApiResponse response = ApiResponse.builder()
                .data(promotionMapper.toPromotionDTO(promotion))
                .message("update promotion successful")
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

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getPromotionById(@PathVariable Long id) {
        Promotion byId = promotionService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(promotionMapper.toPromotionDTO(byId))
                .message("get promotion successful")
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
                .data(promotions.stream().map(promotionMapper::toPromotionDTO))
                .message("get all promotions successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
