package com.anthony.calorie_counter.entity.dto.request;

import com.anthony.calorie_counter.service.validation.ConsumptionValid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConsumptionValid
public class ConsumptionDto {
    @Positive(message = "MealId must be a positive number greater than zero.")
    private Long mealId;
    @PositiveOrZero(message = "Grams cannot be a negative number.")
    private Integer grams;
    @Positive(message = "AlimentId must be a positive number greater than zero.")
    private Long alimentId;
    @Positive(message = "DailyConsumeId must be a positive number greater than zero.")
    private Long dailyConsumeId;
}
