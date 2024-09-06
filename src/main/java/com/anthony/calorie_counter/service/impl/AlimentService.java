package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFound;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.service.interfaces.IAlimentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentService implements IAlimentService {
    private final AlimentRepository alimentRepository;

    public AlimentService(AlimentRepository alimentRepository) {
        this.alimentRepository = alimentRepository;
    }

    @Override
    public AlimentModel create(AlimentModel aliment) {
        return alimentRepository.save(aliment);
    }

    @Override
    public AlimentModel findById(Long id) {
        return alimentRepository.findById(id).orElseThrow(
                () -> new EntityDataNotFound(ExceptionMessages.ALIMENT_NOT_FOUND_WITH_ID + id)
        );
    }

    @Override
    public List<AlimentModel> findByName(String name) {
        return alimentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public AlimentModel update(Long id, AlimentModel aliment) {
        AlimentModel current = alimentRepository.getReferenceById(id);
        current.setName(aliment.getName());
        current.setPortion(aliment.getPortion());
        current.setCalories(aliment.getCalories());
        current.setTotalFat(aliment.getTotalFat());
        current.setProtein(aliment.getProtein());
        current.setCarbohydrate(aliment.getCarbohydrate());
        current.setFiber(aliment.getFiber());
        current.setSugars(aliment.getSugars());
        return alimentRepository.save(current);
    }

    @Override
    public void delete(Long id) {
        if (!alimentRepository.existsById(id)) {
            throw new EntityDataNotFound(ExceptionMessages.ALIMENT_NOT_FOUND_WITH_ID + id);
        }
        alimentRepository.deleteById(id);
    }
}
