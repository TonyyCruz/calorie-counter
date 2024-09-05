package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.controller.AlimentController;
import com.anthony.calorie_counter.dto.request.aliment.AlimentDto;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        @DisplayName("Test if is possible create a new aliment")
        void testCanCreateANewFood() {
            AlimentDto alimentDto = AlimentFactory.alimentDto();
            AlimentModel createdFood = AlimentFactory.alimentEntityFromDto(alimentDto);
            AlimentViewDto expect = new AlimentViewDto(createdFood);
            when(alimentService.create(alimentDto.toEntity())).thenReturn(createdFood);
            ResponseEntity<AlimentViewDto> received = alimentController.create(alimentDto);
            AlimentViewDto current = Objects.requireNonNull(received.getBody());
            verify(alimentService, times(1)).create(alimentDto.toEntity());
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
        @DisplayName("Test if is possible find an aliment by id")
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

        @Test
        @DisplayName("Test if is possible find an aliment by name")
        void testCanFindAnAlimentByName() {
            String name = "Burger from Mexico";
            AlimentModel aliment = AlimentFactory.createAlimentEntity();
            aliment.setName(name);
            List<AlimentViewDto> expect = List.of(new AlimentViewDto(aliment));
            when(alimentService.findByName("mexico")).thenReturn(List.of(aliment));
            ResponseEntity<List<AlimentViewDto>> received = alimentController.findByName("mexico");
            List<AlimentViewDto> current = Objects.requireNonNull(received.getBody());
            verify(alimentService, times(1)).findByName("mexico");
            assertEquals(expect, current);
        }

        @Test
        @DisplayName("Test if is possible update an aliment by id")
        void testCanUpdateAnAlimentById() {
            Long id = 1L;
            AlimentDto update = AlimentFactory.alimentDto();
            AlimentModel expect =AlimentFactory.alimentEntityFromDto(update);
            expect.setId(id);
            when(alimentService.update(id, update.toEntity())).thenReturn(expect);
            ResponseEntity<AlimentViewDto> received = alimentController.update(id, update);
            AlimentViewDto current = Objects.requireNonNull(received.getBody());
            verify(alimentService, times(1)).update(id, update.toEntity());
            assertEquals(new AlimentViewDto(expect), current);
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
            AlimentModel aliment = AlimentFactory.alimentDto().toEntity();
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

        @Test
        @DisplayName("Test if can find an aliment by name")
        void testCanFindAnAlimentByName() {
            String name = "Burger from Mexico";
            AlimentModel expect = AlimentFactory.createAlimentEntity();
            expect.setName(name);
            when(alimentRepository.findByNameContainingIgnoreCase("mexico")).thenReturn(List.of(expect));
            Optional<AlimentModel> received = alimentService.findByName("mexico").stream().findFirst();
            AlimentModel current = Objects.requireNonNull(received.orElse(null));
            verify(alimentRepository, times(1)).findByNameContainingIgnoreCase("mexico");
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
        @DisplayName("Test if can update an aliment by Id")
        void testCanUpdateAnAlimentById() {
            AlimentModel alimentBeforeUpdate = AlimentFactory.createAlimentEntity();
            AlimentModel update = AlimentFactory.alimentDto().toEntity();
            AlimentModel expect =AlimentFactory.cloneAlimentModel(update);
            expect.setId(alimentBeforeUpdate.getId());
            when(alimentRepository.getReferenceById(alimentBeforeUpdate.getId())).thenReturn(alimentBeforeUpdate);
            when(alimentRepository.save(expect)).thenReturn(expect);
            AlimentModel current = alimentService.update(alimentBeforeUpdate.getId(), update);
            verify(alimentRepository, times(1)).getReferenceById(alimentBeforeUpdate.getId());
            verify(alimentRepository, times(1)).save(expect);
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
