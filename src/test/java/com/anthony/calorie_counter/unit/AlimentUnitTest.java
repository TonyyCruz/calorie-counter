package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.controller.AlimentController;
import com.anthony.calorie_counter.dto.request.aliment.AlimentCreateDto;
import com.anthony.calorie_counter.dto.response.aliment.AlimentViewDto;
import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.service.impl.AlimentService;
import com.anthony.calorie_counter.utils.factories.AlimentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for aliment")
public class AlimentUnitTest {
    @Mock
    AlimentRepository alimentRepository;

    @Nested
    @DisplayName("Aliment Controller unit tests")
    class AlimentControllerTest {
        @InjectMocks
        AlimentController alimentController;

        @Mock
        AlimentService alimentService;

        @Test
        @DisplayName("Test if is possible create a new aliment.")
        void testCanCreateANewFood() {
            AlimentCreateDto alimentCreateDto = AlimentFactory.alimentCreateDto();
            AlimentModel createdFood = AlimentFactory.alimentEntityFromDto(alimentCreateDto);
            AlimentViewDto expect = new AlimentViewDto(createdFood);
            when(alimentService.create(alimentCreateDto.toEntity())).thenReturn(createdFood);
            ResponseEntity<AlimentViewDto> received = alimentController.create(alimentCreateDto);
            AlimentViewDto current = Objects.requireNonNull(received.getBody());
            verify(alimentService, times(1)).create(alimentCreateDto.toEntity());
            assertNotNull(current.id());
            assertEquals(expect.id(), current.id());
            assertEquals(expect.name(), current.name());
            assertEquals(expect.portion(), current.portion());
            assertEquals(expect.calories(), current.calories());
            assertEquals(expect.totalFat(), current.totalFat());
            assertEquals(expect.protein(), current.protein());
            assertEquals(expect.calories(), current.calories());
            assertEquals(expect.fiber(), current.fiber());
            assertEquals(expect.sugars(), current.sugars());
        }
    }
}
