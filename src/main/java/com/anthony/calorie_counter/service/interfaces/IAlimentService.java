package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.AlimentModel;

import java.util.List;

public interface IAlimentService {
    AlimentModel create(AlimentModel aliment);
    AlimentModel findById(Long id);
    List<AlimentModel> findByName(String  name);
    AlimentModel update(Long id, AlimentModel aliment);
    void delete(Long id);
}
