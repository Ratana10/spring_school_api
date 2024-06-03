package com.piseth.schoolapi.service;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.studytypes.StudyType;
import com.piseth.schoolapi.studytypes.StudyTypeRepository;
import com.piseth.schoolapi.studytypes.StudyTypeService;
import com.piseth.schoolapi.studytypes.StudyTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyTypeServiceTest {
    @Mock
    private StudyTypeRepository studyTypeRepository;

    private StudyTypeService studyTypeService;

    @BeforeEach
    public void setUp() {
        studyTypeService = new StudyTypeServiceImpl(studyTypeRepository);
    }

    @Test
    public void createTest() {
        StudyType studyType = new StudyType();
        studyType.setName("Online");

        //call service
        studyTypeService.create(studyType);

        //verify
        verify(studyTypeRepository, times(1)).save(studyType);
    }

    @Test
    public void updateTest() {
        StudyType studyType = new StudyType();
        studyType.setId(1L);
        studyType.setName("Online");

        StudyType studyType2=new StudyType();
        studyType2.setId(1L);
        studyType2.setName("Online updated");

        //when
        when(studyTypeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(studyType));

        when(studyTypeRepository.save(any(StudyType.class)))
                .thenReturn(studyType);

        StudyType updated = studyTypeService.update(1L, studyType2);

        //then
        assertEquals(1L, updated.getId());
        assertEquals("Online updated", updated.getName());
    }

    @Test
    void deleteTest(){
        //given
        StudyType studyType=new StudyType();
        studyType.setId(1L);
        studyType.setName("Online");

        //when
        when(studyTypeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(studyType));

        studyTypeService.delete(1L);

        //then
        verify(studyTypeRepository, times(1)).deleteById(1L);
        verify(studyTypeRepository, times(1)).findById(1L);
    }

    @Test
    void deleteNotFoundTest(){
        //when
        when(studyTypeRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->studyTypeService.getById(any(Long.class)));


        //then
        verify(studyTypeRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void getByIdTest(){
        //given
        StudyType studyType=new StudyType();
        studyType.setId(1L);
        studyType.setName("Online");

        //when
        when(studyTypeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(studyType));

        StudyType byId = studyTypeService.getById(1L);

        //then
        assertEquals(1L, byId.getId());
        assertEquals("Online", byId.getName());
    }

    @Test
    void getByIdNotFoundTest(){
        //given


        //when
        when(studyTypeRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());


        //then
        assertThrows(ResourceNotFoundException.class, ()->studyTypeService.getById(1L));
        verify(studyTypeRepository, times(1)).findById(any(Long.class));

    }

    @Test
    void getAllStudyTypesTest(){
        //given
        StudyType studyType=new StudyType();
        studyType.setId(1L);
        studyType.setName("Online");

        StudyType studyType2=new StudyType();
        studyType2.setId(1L);
        studyType2.setName("Youtube");

        List<StudyType> studyTypeList = Arrays.asList(studyType, studyType2);

        //when
        when(studyTypeRepository.findAll())
                .thenReturn(studyTypeList);
        List<StudyType> studyTypes = studyTypeService.getStudyTypes();

        //then
        assertEquals(studyTypeList, studyTypes);
    }

}
