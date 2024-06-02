package com.piseth.schoolapi.auditing;


import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.categories.CategoryRepository;
import com.piseth.schoolapi.categories.CategoryService;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl  {

    private final TestRepository testRepository;

    public Test create(Test test) {
        return testRepository.save(test);
    }

    public Test update(Test test) {
        Test notFoud = testRepository.findById(test.getId())
                .orElseThrow(() -> new ResourceNotFoundException("not foud", test.getId()));

        notFoud = test;
        return testRepository.save(notFoud);
    }

    public void delete(Long id) {
        Test notFoud = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not foud", id));

         testRepository.deleteById(id);
    }


    public List<Test> getAll() {
        List<Test> all = testRepository.findAll();
        return all;
    }
}
