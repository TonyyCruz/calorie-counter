package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.entity.dto.request.DailyConsumeDto;
import com.anthony.calorie_counter.entity.dto.response.DailyConsumeViewDto;
import com.anthony.calorie_counter.service.interfaces.IDailyConsumeService;
import com.anthony.calorie_counter.utils.UserAuthorizationManager;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dailyconsume")
public class DailyConsumeController {
    private final IDailyConsumeService dailyConsumeService;

    @PostMapping
    public ResponseEntity<DailyConsumeViewDto> create(@RequestBody DailyConsumeDto dailyConsumeDto) {
        DailyConsumeModel dailyConsume = dailyConsumeService.create(UserAuthorizationManager.getPrincipalId(), dailyConsumeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DailyConsumeViewDto(dailyConsume));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyConsumeViewDto> findById(@PathVariable Long id) {
        DailyConsumeModel dailyConsume = dailyConsumeService.findById(id);
        return ResponseEntity.ok(new DailyConsumeViewDto(dailyConsume));
    }

    @GetMapping
    public ResponseEntity<List<DailyConsumeViewDto>> findAllByUserId(@PathParam("userId")UUID userId) {
        List<DailyConsumeViewDto> dailyConsumes = dailyConsumeService.findAllByUserId(userId)
                .stream().map(DailyConsumeViewDto::new).toList();
        return ResponseEntity.ok(dailyConsumes);
    }

    @GetMapping("/userdate")
    public ResponseEntity<DailyConsumeViewDto> findByUserIdAndDate(
            @PathParam("userId")UUID userId,
            @PathParam("date") Instant date
    ) {
        DailyConsumeModel dailyConsume = dailyConsumeService.findByUserIdAndDate(userId, date);
        return ResponseEntity.ok(new DailyConsumeViewDto(dailyConsume));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        dailyConsumeService.delete(id, UserAuthorizationManager.getPrincipalId());
        return ResponseEntity.noContent().build();
    }
}
