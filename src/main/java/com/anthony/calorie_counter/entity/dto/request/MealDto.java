package com.anthony.calorie_counter.entity.dto.request;

import com.anthony.calorie_counter.service.validation.MealValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MealValid
public class MealDto implements Serializable {
    private Long descriptionId;
    private Long dailyConsumeId;
}
