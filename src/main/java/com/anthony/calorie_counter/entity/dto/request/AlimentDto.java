package com.anthony.calorie_counter.entity.dto.request;

import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.service.validation.AlimentStringFieldValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimentDto implements Serializable {
    @NotBlank(message = "The name must not be empty.")
    @Length(min = 2, max = 50, message = "The name length must be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "The portion must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of portion.")
    private String portion;
    @PositiveOrZero(message = "The calories must not be a negative number.")
    @NotNull(message = "The calories must not be null.")
    private Integer calories;
    @NotBlank(message = "The totalFat must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of totalFat.")
    private String totalFat;
    @NotBlank(message = "The protein must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of protein.")
    private String protein;
    @NotBlank(message = "The carbohydrate must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of carbohydrate.")
    private String  carbohydrate;
    @NotBlank(message = "The fiber must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of fiber.")
    private String  fiber;
    @NotBlank(message = "The sugars must not be empty.")
    @AlimentStringFieldValid(message = "Invalid received format of sugars.")
    private String  sugars;

    public AlimentModel toEntity() {
        AlimentModel food = new AlimentModel();
        food.setName(name);
        food.setPortion(portion.toLowerCase());
        food.setCalories(calories);
        food.setTotalFat(totalFat.toLowerCase());
        food.setProtein(protein.toLowerCase());
        food.setCarbohydrate(carbohydrate.toLowerCase());
        food.setFiber(fiber.toLowerCase());
        food.setSugars(sugars.toLowerCase());
        return food;
    }
}
