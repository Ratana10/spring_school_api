package com.piseth.schoolapi.schedules;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule create(Schedule schedule);
    Schedule update(Long id, Schedule schedule);
    Optional<Schedule> getById(Long id);
    List<Schedule> getAll();
    List<Schedule> getScheduleByCourseId(Long courseId);
    void delete(Long id);

}
