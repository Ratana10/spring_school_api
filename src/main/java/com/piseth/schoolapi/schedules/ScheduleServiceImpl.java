package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    @Override
    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {

        Optional<Schedule> byId = getById(id);

        return byId.map(sch ->{
            sch.setDay(schedule.getDay());
            sch.setStartTime(schedule.getStartTime());
            sch.setEndTime(schedule.getEndTime());
            sch.setCourse(schedule.getCourse());

            return scheduleRepository.save(sch);
        }).orElse(null);
    }

    @Override
    public Optional<Schedule> getById(Long id) {
        return Optional.ofNullable(scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", id)));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        scheduleRepository.deleteById(id);
    }
}
