package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.entity.dto.request.MealDto;
import com.anthony.calorie_counter.entity.dto.response.MealViewDto;
import com.anthony.calorie_counter.service.interfaces.IMealService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/meals")
public class MealController {
    private final IMealService mealService;

    @PostMapping
    public ResponseEntity<MealViewDto> create(@RequestBody @Valid MealDto mealDto) {
        MealModel meal = mealService.create(mealDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MealViewDto(meal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealViewDto> findById(@PathVariable Long id) {
        MealModel meal = mealService.findById(id);
        return ResponseEntity.ok(new MealViewDto(meal));
    }

    @GetMapping
    public ResponseEntity<List<MealViewDto>> findAllByDailyConsumeId(@PathParam("dailyConsumeId") Long dailyConsumeId) {
        List<MealViewDto> mealList = mealService.findAllByDailyConsumeId(dailyConsumeId)
                .stream().map(MealViewDto::new).toList();
        return ResponseEntity.ok(mealList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealViewDto> update(@RequestBody @Valid MealDto mealDto, @PathVariable Long id) {
        MealModel meal = mealService.update(id, mealDto);
        return ResponseEntity.ok(new MealViewDto(meal));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        mealService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
