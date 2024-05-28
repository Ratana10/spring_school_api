package com.piseth.schoolapi.studytypes;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyTypeServiceImpl implements StudyTypeService {

    private final StudyTypeRepository studyTypeRepository;

    @Override
    public StudyType create(StudyType studyType) {
        return studyTypeRepository.save(studyType);
    }

    @Override
    public StudyType update(Long id, StudyType studyType) {
        StudyType byId = getById(id);

        byId.setName(studyType.getName());
        studyTypeRepository.save(byId);
        return byId;

    }

    @Override
    public void delete(Long id) {

        studyTypeRepository.deleteById(id);

    }

    @Override
    public StudyType getById(Long id) {
        return studyTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StudyType", id));
    }

    @Override
    public List<StudyType> getStudyTypes() {
        return studyTypeRepository.findAll();
    }
}
