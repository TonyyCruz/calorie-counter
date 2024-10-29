package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.entity.dto.request.DailyConsumeDto;
import com.anthony.calorie_counter.entity.dto.response.DailyConsumeViewDto;
import com.anthony.calorie_counter.service.interfaces.IDailyConsumeService;
import com.anthony.calorie_counter.utils.UserAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dailyconsume")
public class DailyConsumeController extends UserAuthorizationManager {
    private final IDailyConsumeService dailyConsumeService;

    @PostMapping
    public ResponseEntity<DailyConsumeViewDto> create(@RequestBody DailyConsumeDto dailyConsumeDto) {
        DailyConsumeModel dailyConsume = dailyConsumeService.create(getPrincipalId(), dailyConsumeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DailyConsumeViewDto(dailyConsume));
    }
}
