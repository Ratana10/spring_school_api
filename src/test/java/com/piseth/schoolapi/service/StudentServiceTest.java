package com.piseth.schoolapi.service;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.students.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;
    private Student student;
    private Student updateStudent;
    @BeforeEach
    void init(){
        student = new Student();
        student.setId(1L);
        student.setName("Tola");
        student.setEmail("tola@gmail.com");
        student.setStudentType(StudentType.STUDY);
        student.setGender(Gender.MALE);

        updateStudent = new Student();
        updateStudent.setName("Tola update");
        updateStudent.setEmail("tolaupdated@gmail.com");
        updateStudent.setStudentType(StudentType.STUDY);
        updateStudent.setGender(Gender.MALE);
    }

    @Test
    public void createSuccessTest(){

        when(studentRepository.existsByEmail(student.getEmail()))
                .thenReturn(false);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        Student savedStudent = studentService.create(student);

        assertNotNull(savedStudent);
        assertEquals(savedStudent.getName(), "Tola");
    }

    @Test
    public void createFailTest(){

        when(studentRepository.existsByEmail(student.getEmail()))
                .thenReturn(true);

        assertThrows(ApiException.class, ()-> studentService.create(student));

        verify(studentRepository, never()).save(student);

    }

    @Test
    public void updateSuccessTest(){


        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(studentRepository.existsByEmailAndIdNot(updateStudent.getEmail(), 1L))
                .thenReturn(false);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        Student updatedStudent = studentService.update(1L, updateStudent);

        assertNotNull(updatedStudent);
        assertEquals("tolaupdated@gmail.com", updatedStudent.getEmail());

    }
    @Test
    public void updateFailTest(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(studentRepository.existsByEmailAndIdNot(updateStudent.getEmail(), 1L))
                .thenReturn(true);

        assertThrows(ApiException.class, ()-> studentService.update(1L, updateStudent));

        verify(studentRepository, never()).save(student);
    }

    @Test
    public void deleteSuccessTest(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        studentService.delete(1L);

        verify(studentRepository, times(1))
                .deleteById(1L);
    }

    @Test
    public void deleteFailTest(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> studentService.getById(1L));

        verify(studentRepository, never())
                .deleteById(1L);

    }

    @Test
    public void getByIdSuccessTest(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        Student byId = studentService.getById(1L);

        assertNotNull(byId);
        verify(studentRepository, times(1))
                .findById(1L);
    }

    @Test
    public void getByIdFailTest(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> studentService.getById(1L));

        verify(studentRepository,  times(1))
                .findById(1L);

    }

    @Test
    public void getStudentsTest(){
        List<Student> list = Arrays.asList(student, updateStudent);

        when(studentRepository.findAll())
                .thenReturn(list);

        List<Student> students = studentService.getStudents();

        assertEquals(list, students);

    }

}