package com.piseth.schoolapi.students;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student toStudent(StudentDTO studentDTO);

    StudentDTO toStudentDTO(Student student);
}
