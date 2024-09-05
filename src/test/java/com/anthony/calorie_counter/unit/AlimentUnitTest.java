package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.controller.AlimentController;
import com.anthony.calorie_counter.dto.request.aliment.AlimentCreateDto;
import com.anthony.calorie_counter.dto.response.aliment.AlimentViewDto;
import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.service.impl.AlimentService;
import com.anthony.calorie_counter.service.interfaces.IAlimentService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for aliment")
public class AlimentUnitTest {

    @Nested
    @DisplayName("Aliment Controller unit tests")
    class AlimentControllerTest {
        @InjectMocks
        AlimentController alimentController;
        @Mock
        IAlimentService alimentService;

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

        @Test
        @DisplayName("Test if is possible find an aliment by id.")
        void testCanFindAnAlimentById() {
            Long id = 1L;
            AlimentModel aliment = AlimentFactory.createAlimentEntity();
            aliment.setId(id);
            AlimentViewDto expect = new AlimentViewDto(aliment);
            when(alimentService.findById(id)).thenReturn(aliment);
            ResponseEntity<AlimentViewDto> received = alimentController.findById(id);
            AlimentViewDto current = Objects.requireNonNull(received.getBody());
            verify(alimentService, times(1)).findById(id);
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

    @Nested
    @DisplayName("Aliment Service unit tests")
    class AlimentServiceTest {
        @Mock
        AlimentRepository alimentRepository;
        @InjectMocks
        AlimentService alimentService;

        @Test
        @DisplayName("Test if can save a new aliment")
        void testCanSaveANewAliment() {
            AlimentModel aliment = AlimentFactory.alimentCreateDto().toEntity();
            AlimentModel expect = AlimentFactory.cloneAlimentModel(aliment);
            expect.setId(1L);
            when(alimentRepository.save(aliment)).thenReturn(expect);
            AlimentModel current = alimentService.create(aliment);
            verify(alimentRepository, times(1)).save(aliment);
            assertNotNull(current.getId());
            assertEquals(expect.getId(), current.getId());
            assertEquals(expect.getName(), current.getName());
            assertEquals(expect.getPortion(), current.getPortion());
            assertEquals(expect.getCalories(), current.getCalories());
            assertEquals(expect.getTotalFat(), current.getTotalFat());
            assertEquals(expect.getProtein(), current.getProtein());
            assertEquals(expect.getCalories(), current.getCalories());
            assertEquals(expect.getFiber(), current.getFiber());
            assertEquals(expect.getSugars(), current.getSugars());
        }

        @Test
        @DisplayName("Test if can find an aliment by id")
        void testCanFindAnAlimentById() {
            Long id = 1L;
            AlimentModel expect = AlimentFactory.createAlimentEntity();
            expect.setId(id);
            when(alimentRepository.findById(id)).thenReturn(Optional.of(expect));
            AlimentModel current = alimentService.findById(id);
            verify(alimentRepository, times(1)).findById(id);
            assertNotNull(current.getId());
            assertEquals(expect.getId(), current.getId());
            assertEquals(expect.getName(), current.getName());
            assertEquals(expect.getPortion(), current.getPortion());
            assertEquals(expect.getCalories(), current.getCalories());
            assertEquals(expect.getTotalFat(), current.getTotalFat());
            assertEquals(expect.getProtein(), current.getProtein());
            assertEquals(expect.getCalories(), current.getCalories());
            assertEquals(expect.getFiber(), current.getFiber());
            assertEquals(expect.getSugars(), current.getSugars());
        }
    }
}
