package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.meal.MealCreateDto;
import com.anthony.calorie_counter.dto.response.meal.MealViewDto;
import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.service.interfaces.IFoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meals")
public class MealController {
    private final IFoodService foodService;

    public MealController(IFoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/create")
    public ResponseEntity<MealViewDto> create(@Valid @RequestBody MealCreateDto createDto) {
        MealModel food = foodService.create(createDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MealViewDto(food));
    }
}
