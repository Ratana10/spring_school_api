package com.piseth.schoolapi.studytypes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<StudyType> byId = getById(id);
        if(byId.isPresent()){
            byId.get().setName(studyType.getName());
            studyTypeRepository.save(byId.get());
            return byId.get();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<StudyType> byId = getById(id);
        if (byId.isPresent()){
            studyTypeRepository.deleteById(id);
        }
    }

    @Override
    public Optional<StudyType> getById(Long id) {
        return Optional.ofNullable(studyTypeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("NOT found")));
    }

    @Override
    public List<StudyType> getStudyTypes() {
        return studyTypeRepository.findAll();
    }
}
