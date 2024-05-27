package com.piseth.schoolapi.schedules;

import java.util.Optional;

public interface ScheduleService {
    Schedule create(Schedule schedule);
    Schedule update(Long id, Schedule schedule);
    Optional<Schedule> getById(Long id);
    void delete(Long id);

}
