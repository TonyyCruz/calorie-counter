package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.AlimentModel;

public interface IAlimentService {
    AlimentModel create(AlimentModel aliment);
    AlimentModel findById(Long id);
    AlimentModel update(Long id, AlimentModel aliment);
    void delete(Long id);
}
