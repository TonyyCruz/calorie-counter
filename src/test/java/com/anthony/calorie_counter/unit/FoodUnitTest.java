package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.controller.FoodController;
import com.anthony.calorie_counter.dto.request.food.FoodCreateDto;
import com.anthony.calorie_counter.dto.response.food.FoodViewDto;
import com.anthony.calorie_counter.entity.FoodModel;
import com.anthony.calorie_counter.repository.FoodRepository;
import com.anthony.calorie_counter.service.impl.FoodService;
import com.anthony.calorie_counter.utils.factories.FoodFactory;
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
@DisplayName("Unit test for Food")
public class FoodUnitTest {
//    @InjectMocks
//    FoodService foodService;

    @Mock
    FoodRepository foodRepository;

    @Nested
    @DisplayName("Food Controller unit tests")
    class FoodControllerTest {
        @InjectMocks
        FoodController foodController;

        @Mock
        FoodService foodService;

        @Test
        @DisplayName("Test if is possible create a new food.")
        void testCanCreateANewFood() {
            FoodCreateDto foodCreateDto = FoodFactory.foodCreateDto();
            FoodModel createdFood = FoodFactory.foodEntityFromDto(foodCreateDto);
            FoodViewDto expect = new FoodViewDto(createdFood);
            when(foodService.create(foodCreateDto.toEntity())).thenReturn(createdFood);
            ResponseEntity<FoodViewDto> received = foodController.create(foodCreateDto);
            FoodViewDto current = Objects.requireNonNull(received.getBody());
            verify(foodService, times(1)).create(foodCreateDto.toEntity());
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
