package com.piseth.schoolapi.promotion;

public interface PromotionService {
    Promotion create(Promotion promotion);

    Promotion update(Long id, Promotion promotion);

    void delete(Long id);

    Promotion getById(Long id);

}
