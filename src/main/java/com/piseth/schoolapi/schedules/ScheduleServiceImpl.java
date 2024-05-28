package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CourseService courseService;

    @Override
    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {

        Schedule byId = getById(id);

        byId.setDay(schedule.getDay());
        byId.setStartTime(schedule.getStartTime());
        byId.setEndTime(schedule.getEndTime());
        byId.setCourse(schedule.getCourse());

        return scheduleRepository.save(byId);

    }

    @Override
    public Schedule getById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", id));
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleByCourseId(Long courseId) {
        courseService.getById(courseId);
        return scheduleRepository.findByCourseId(courseId);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        scheduleRepository.deleteById(id);
    }
}
