package com.emetaplus.workplace.builder.schedule.repository;

import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
   Optional<Schedule> findOneByProfile(Profile profile);
}
