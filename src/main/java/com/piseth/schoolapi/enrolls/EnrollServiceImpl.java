package com.piseth.schoolapi.enrolls;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {

    private final EnrollRepository enrollRepository;

    @Override
    public Enroll create(Enroll enroll) {

        return enrollRepository.save(enroll);
    }

    @Override
    public Enroll update(Long id, Enroll enroll) {

        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Enroll> byId = getById(id);
        if (byId.isPresent()) {
            enrollRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Enroll> getById(Long id) {
        return Optional.ofNullable(enrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enroll", id)));
    }

    @Override
    public List<Enroll> getEnrollByCourseId() {
        return null;
    }

    @Override
    public List<Enroll> getEnrollByStudentId() {
        return null;
    }

}
