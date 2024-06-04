package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {CourseService.class, PromotionService.class}
)
public interface PromotionMapper {
    @Mappings({
            @Mapping(source = "promotionCourseIds", target = "promotionCourses"),
            @Mapping(source = "requiredCourseIds", target = "requiredCourses")
    })
    Promotion toPromotion(PromotionDTO promotionDTO);

    @Mappings({
            @Mapping(source = "promotionCourses", target = "promotionCourseIds", qualifiedByName = "courseSetToCourseIdSet"),
            @Mapping(source = "requiredCourses", target = "requiredCourseIds", qualifiedByName = "courseSetToCourseIdSet")
    })
    PromotionDTO toPromotionDTO(Promotion promotion);

    @Named("courseSetToCourseIdSet")
    default Set<Long> courseSetToCourseIdSet(Set<Course> courses) {
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }
}
