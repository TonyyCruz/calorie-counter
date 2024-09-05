package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.aliment.AlimentCreateDto;
import com.anthony.calorie_counter.dto.response.aliment.AlimentViewDto;
import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.service.interfaces.IAlimentService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aliments")
public class AlimentController {
    private final IAlimentService alimentService;

    public AlimentController(IAlimentService alimentService) {
        this.alimentService = alimentService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<AlimentViewDto> create(@Valid @RequestBody AlimentCreateDto createDto) {
        AlimentModel food = alimentService.create(createDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AlimentViewDto(food));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentViewDto> findById(@PathVariable Long id) {
        AlimentModel aliment = alimentService.findById(id);
        return ResponseEntity.ok(new AlimentViewDto(aliment));
    }

    @GetMapping
    public ResponseEntity<List<AlimentViewDto>> findByName(@PathParam("name") String name) {
        List<AlimentViewDto> aliments = alimentService.findByName(name).stream().map(AlimentViewDto::new).toList();
        return ResponseEntity.ok(aliments);
    }
}
