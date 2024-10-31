package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.entity.dto.request.DailyConsumeDto;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.DailyConsumeRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.interfaces.IDailyConsumeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyConsumeService implements IDailyConsumeService {
    private final DailyConsumeRepository dailyConsumeRepository;
    private final UserRepository userRepository;

    @Override
    public DailyConsumeModel create(UUID userId, DailyConsumeDto dailyConsumeDto) {
        DailyConsumeModel dailyConsume = new DailyConsumeModel();
        dailyConsume.setDate(dailyConsumeDto.getDate());
        dailyConsume.setUser(userRepository.getReferenceById(userId));
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
    public DailyConsumeModel findByUserIdAndDate(UUID userId, Instant date) {
        return dailyConsumeRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new EntityDataNotFoundException(
                        ExceptionMessages.DATA_NOT_FOUND_WITH_ID + userId + " and date: " + date)
                );
    }

//    @Override
//    public DailyConsumeModel update(Long id, DailyConsumeModel dailyConsume) {
//        try {
//            DailyConsumeModel current = dailyConsumeRepository.getReferenceById(id);
//            current.setUser(dailyConsume.getUser());
//            current.setDate(dailyConsume.getDate());
//            return dailyConsumeRepository.save(current);
//        } catch (EntityNotFoundException e) {
//            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
//        }
//    }

    @Override
    public void delete(Long id) {
        if (!dailyConsumeRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
        dailyConsumeRepository.deleteById(id);
    }
}
