package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.entity.dto.request.MealDto;
import com.anthony.calorie_counter.entity.dto.response.MealViewDto;
import com.anthony.calorie_counter.service.interfaces.IMealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
