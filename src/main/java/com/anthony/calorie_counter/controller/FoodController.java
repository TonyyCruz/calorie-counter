package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.food.FoodCreateDto;
import com.anthony.calorie_counter.dto.response.food.FoodViewDto;
import com.anthony.calorie_counter.entity.FoodModel;
import com.anthony.calorie_counter.service.interfaces.IFoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {
    private final IFoodService foodService;

    public FoodController(IFoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/create")
    public ResponseEntity<FoodViewDto> create(@Valid @RequestBody FoodCreateDto createDto) {
        FoodModel food = foodService.create(createDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new FoodViewDto(food));
    }
}
