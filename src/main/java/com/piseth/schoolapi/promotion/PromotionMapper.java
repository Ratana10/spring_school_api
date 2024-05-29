package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {CourseService.class}
)
public interface PromotionMapper {
    @Mappings({
            @Mapping(source = "promotionCourseIds", target = "promotionCourses", qualifiedByName = "courseIdSetToCourseSet"),
            @Mapping(source = "requiredCourseIds", target = "requiredCourses", qualifiedByName = "courseIdSetToCourseSet")
    })
    Promotion toPromotion(PromotionDTO promotionDTO, @Context CourseService courseService);

    @Mappings({
            @Mapping(source = "promotionCourses", target = "promotionCourseIds", qualifiedByName = "courseSetToCourseIdSet"),
            @Mapping(source = "requiredCourses", target = "requiredCourseIds", qualifiedByName = "courseSetToCourseIdSet")
    })
    PromotionDTO toPromotionDTO(Promotion promotion);

    @Named("courseIdSetToCourseSet")
    default Set<Course> courseIdSetToCourseSet(Set<Long> courseIds, @Context CourseService courseService) {
        return courseService.findCoursesByIds(courseIds);
    }

    @Named("courseSetToCourseIdSet")
    default Set<Long> courseSetToCourseIdSet(Set<Course> courses) {
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }
}
