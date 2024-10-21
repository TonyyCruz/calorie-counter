package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.dto.request.ConsumptionDto;
import com.anthony.calorie_counter.entity.dto.response.ConsumptionViewDto;
import com.anthony.calorie_counter.service.interfaces.IConsumptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consumption")
public class ConsumptionController {
    private final IConsumptionService consumptionService;

    @PostMapping
    public ResponseEntity<ConsumptionViewDto> create(@RequestBody @Valid ConsumptionDto consumptionDto) {
        ConsumptionModel consumption = consumptionService.create(consumptionDto);
        ConsumptionViewDto view = new ConsumptionViewDto(consumption);
        return ResponseEntity.status(HttpStatus.CREATED).body(view);
    }
}
