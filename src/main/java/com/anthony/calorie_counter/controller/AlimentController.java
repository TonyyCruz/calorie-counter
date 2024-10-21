package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.dto.request.AlimentDto;
import com.anthony.calorie_counter.entity.dto.response.AlimentViewDto;
import com.anthony.calorie_counter.entity.AlimentModel;
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

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<AlimentViewDto> create(@Valid @RequestBody AlimentDto createDto) {
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<AlimentViewDto> update(@PathVariable Long id, @Valid @RequestBody AlimentDto alimentDto) {
        AlimentModel aliment = alimentService.update(id, alimentDto.toEntity());
        return ResponseEntity.ok(new AlimentViewDto(aliment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        alimentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
