package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.dto.request.ConsumptionDto;
import com.anthony.calorie_counter.entity.dto.response.ConsumptionViewDto;
import com.anthony.calorie_counter.service.interfaces.IConsumptionService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consumptions")
public class ConsumptionController {
    private final IConsumptionService consumptionService;

    @PostMapping
    public ResponseEntity<ConsumptionViewDto> create(@RequestBody @Valid ConsumptionDto consumptionDto) {
        ConsumptionModel consumption = consumptionService.create(consumptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ConsumptionViewDto(consumption));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumptionViewDto> findById(@PathVariable Long id) {
        ConsumptionModel consumption = consumptionService.findById(id);
        return ResponseEntity.ok(new ConsumptionViewDto(consumption));
    }

    @GetMapping
    public ResponseEntity<List<ConsumptionViewDto>> findAllByMealId(@PathParam("mealId") Long mealId) {
        List<ConsumptionViewDto> consumptionList = consumptionService.findAllByMealId(mealId)
                .stream().map(ConsumptionViewDto::new).toList();
        return ResponseEntity.ok(consumptionList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumptionViewDto> update(@PathVariable Long id, @RequestBody @Valid ConsumptionDto consumptionDto) {
        ConsumptionModel consumption = consumptionService.update(id, consumptionDto);
        return ResponseEntity.ok(new ConsumptionViewDto(consumption));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        consumptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
