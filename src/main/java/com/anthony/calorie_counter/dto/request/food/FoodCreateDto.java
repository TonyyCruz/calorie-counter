package com.anthony.calorie_counter.dto.request.food;

import com.anthony.calorie_counter.entity.FoodModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCreateDto implements Serializable {
    @NotBlank(message = "The name must not be empty.")
    private String name;
    @NotBlank(message = "The portion must not be empty.")
    private String portion;
    @NotBlank(message = "The calories must not be empty.")
    private Integer calories;
    @NotBlank(message = "The totalFat must not be empty.")
    private String totalFat;
    @NotBlank(message = "The protein must not be empty.")
    private String protein;
    @NotBlank(message = "The carbohydrate must not be empty.")
    private String  carbohydrate;
    @NotBlank(message = "The fiber must not be empty.")
    private String  fiber;
    @NotBlank(message = "The sugars must not be empty.")
    private String  sugars;

    public FoodModel toEntity() {
        FoodModel food = new FoodModel();
        food.setName(name);
        food.setPortion(portion);
        food.setCalories(calories);
        food.setTotalFat(totalFat);
        food.setProtein(protein);
        food.setCarbohydrate(carbohydrate);
        food.setFiber(fiber);
        food.setSugars(sugars);
        return food;
    }
}
