package com.piseth.schoolapi.studytypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudyTypeMapper {
    StudyTypeMapper INSTANCE = Mappers.getMapper(StudyTypeMapper.class);
    StudyType toStudyType(StudyTypeDTO studyTypeDTO);
    StudyTypeDTO toStudyTypeDTO(StudyType studyType);
}
