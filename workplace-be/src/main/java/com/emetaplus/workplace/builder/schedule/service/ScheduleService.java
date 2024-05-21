package com.emetaplus.workplace.builder.schedule.service;

import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.builder.schedule.repository.ScheduleRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.profile.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfileService profileService;

    public Schedule getSchedule() {
        Profile profile = profileService.getMyProfile();
        return scheduleRepository.findOneByProfile(profile).orElseGet(this::initSchedule);
    }

    public void saveTitle(String title) {
        Schedule schedule = getMySchedule();
        schedule.setTitle(title);
        scheduleRepository.save(schedule);
    }

    public void saveShortText(String shortText){
        Schedule schedule = getMySchedule();
        schedule.setShortText(shortText);
        scheduleRepository.save(schedule);
    }

    public void saveScheduleItem(String key, String value) {
        Schedule schedule = getMySchedule();
        try {
            Map<String, String> items = new ObjectMapper().readValue(schedule.getItems(), new TypeReference<>() {});
            items.put(key, value);
            String savedItems = String.valueOf(new JSONObject(items));
            schedule.setItems(savedItems);
            scheduleRepository.save(schedule);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private Schedule initSchedule() {
        Profile profile = profileService.getMyProfile();
        Schedule schedule = new Schedule();
        schedule.setProfile(profile);
        schedule.setItems(String.valueOf(new JSONObject(initScheduleItems())));
        return scheduleRepository.save(schedule);
    }

    private Map<String, String> initScheduleItems() {
        Map<String, String> items = new TreeMap<>();
        for (int i = 1; i <= 7; i++) {
            items.put(String.valueOf(i), "");
        }
        return items;
    }

    private Schedule getMySchedule() {
        Profile profile = profileService.getMyProfile();
        return scheduleRepository.findOneByProfile(profile).orElseThrow(() -> ExceptionHelper.getNotFoundException(Schedule.class));
    }
}
