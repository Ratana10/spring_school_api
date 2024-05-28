package com.piseth.schoolapi.promotion;

import java.util.List;

public interface PromotionService {
    Promotion create(Promotion promotion);

    Promotion update(Long id, Promotion promotion);

    void delete(Long id);

    Promotion getById(Long id);
    List<Promotion> getPromotions();

}
