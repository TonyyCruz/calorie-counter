package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.service.interfaces.IAlimentService;
import org.springframework.stereotype.Service;

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
        return alimentRepository.save(aliment);
    }

    @Override
    public void delete(Long id) {
        alimentRepository.deleteById(id);
    }
}