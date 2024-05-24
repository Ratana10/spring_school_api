package com.piseth.schoolapi.courses;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.categories.CategoryDTO;
import com.piseth.schoolapi.studytypes.StudyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {StudyType.class, Category.class}
)
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    @Mapping(source = "studyTypeId", target = "studyType.id")
    @Mapping(source = "categoryId", target = "category.id")
    Course toCourse(CourseDTO courseDTO);

    @Mapping(source = "studyType.id", target = "studyTypeId")
    @Mapping(source = "category.id", target = "categoryId")
    CourseDTO toCourseDTO(Course course);
}
