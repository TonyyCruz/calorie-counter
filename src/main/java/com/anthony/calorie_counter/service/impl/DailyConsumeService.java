package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.DailyConsumeRepository;
import com.anthony.calorie_counter.service.interfaces.IDailyConsumeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyConsumeService implements IDailyConsumeService {
    private final DailyConsumeRepository dailyConsumeRepository;

    @Override
    public DailyConsumeModel create(DailyConsumeModel dailyConsume) {
        return dailyConsumeRepository.save(dailyConsume);
    }

    @Override
    public DailyConsumeModel findById(Long id) {
        return dailyConsumeRepository.findById(id).orElseThrow(
                () -> new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id)
        );
    }

    @Override
    public List<DailyConsumeModel> findAllByUserId(UUID userId) {
        return dailyConsumeRepository.findAllByUserId(userId);
    }

    @Override
    public DailyConsumeModel updateById(Long id, DailyConsumeModel dailyConsume) {
        try {
            DailyConsumeModel current = dailyConsumeRepository.getReferenceById(id);
            current.setUser(dailyConsume.getUser());
            current.setDate(dailyConsume.getDate());
            return dailyConsumeRepository.save(current);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!dailyConsumeRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
        dailyConsumeRepository.deleteById(id);
    }
}
