package com.codestates.mainproject.group018.somojeon.schedule.service;

import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClubService clubService;

    public ScheduleService(ScheduleRepository scheduleRepository, ClubService clubService) {
        this.scheduleRepository = scheduleRepository;
        this.clubService = clubService;
    }

    public Schedule createSchedule(Schedule schedule, long clubId) {
        schedule.setClub(clubService.findVerifiedClub(clubId));
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        Schedule findSchedule = findVerifiedSchedule(schedule.getScheduleId());

        Optional.ofNullable(schedule.getDate())
                .ifPresent(findSchedule::setDate);
        Optional.ofNullable(schedule.getTime())
                .ifPresent(findSchedule::setTime);
        Optional.ofNullable(schedule.getPlaceName())
                .ifPresent(findSchedule::setPlaceName);
        Optional.ofNullable(schedule.getLongitude())
                .ifPresent(findSchedule::setLongitude);
        Optional.ofNullable(schedule.getLatitude())
                .ifPresent(findSchedule::setLatitude);

        return scheduleRepository.save(findSchedule);
    }

    public Schedule findSchedule(long scheduleId) {
        return findVerifiedSchedule(scheduleId);
    }

    public Page<Schedule> findSchedules(int page, int size) {
        return scheduleRepository.findAll(PageRequest.of(page, size, Sort.by("scheduleId").descending()));
    }

    public void deleteSchedule(long scheduleId) {
        Schedule findSchedule = findSchedule(scheduleId);

        scheduleRepository.delete(findSchedule);
    }

    public Schedule findVerifiedSchedule(long scheduleId) {
        Optional<Schedule> optionalSchedule =
                scheduleRepository.findById(scheduleId);
        Schedule findSchedule = optionalSchedule.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

        return findSchedule;
    }

}
