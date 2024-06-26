package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    @Override
    public Promotion create(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(Long id, Promotion promotion) {
        Promotion byId = getById(id);

        byId.setName(promotion.getName());
        byId.setDescription(promotion.getDescription());
        byId.setRequiredCourses(promotion.getRequiredCourses());
        byId.setPromotionCourses(promotion.getPromotionCourses());
        byId.setDiscountAmount(promotion.getDiscountAmount());
        byId.setDiscountPercentage(promotion.getDiscountPercentage());
        byId.setStartDate(promotion.getStartDate());
        byId.setEndDate(promotion.getEndDate());

        return promotionRepository.save(byId);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        promotionRepository.deleteById(id);
    }

    @Override
    public Promotion getById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Promotion", id));
    }

    @Override
    public List<Promotion> getPromotions() {
        return promotionRepository.findAll();
    }
}
