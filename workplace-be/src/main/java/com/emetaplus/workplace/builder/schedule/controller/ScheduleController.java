package com.emetaplus.workplace.builder.schedule.controller;

import com.emetaplus.workplace.builder.schedule.dto.ScheduleDTO;
import com.emetaplus.workplace.builder.schedule.mapper.ScheduleMapper;
import com.emetaplus.workplace.builder.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @GetMapping("my")
    public ScheduleDTO getSchedule() {
        return scheduleMapper.toDTO(scheduleService.getSchedule());
    }

    @PutMapping("my/title")
    public void saveTitle(@RequestBody String title) {
        scheduleService.saveTitle(title);
    }

    @PutMapping("my/short-text")
    public void saveShortText(@RequestBody String shortText) {
        scheduleService.saveShortText(shortText);
    }

    @PutMapping("my/item/{key}")
    public void saveScheduleItem(@PathVariable String key, @RequestBody String value) {
        scheduleService.saveScheduleItem(key, value);
    }

}
