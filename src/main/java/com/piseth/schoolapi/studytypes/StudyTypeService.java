package com.piseth.schoolapi.studytypes;

import java.util.List;
import java.util.Optional;

public interface StudyTypeService {
    StudyType create(StudyType studyType);
    StudyType update(Long id, StudyType studyType);
    void delete(Long id);
    Optional<StudyType> getById(Long id);
    List<StudyType> getStudyTypes();

}
