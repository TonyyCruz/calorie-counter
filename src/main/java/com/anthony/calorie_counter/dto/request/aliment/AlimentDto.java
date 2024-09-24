package com.anthony.calorie_counter.dto.request.aliment;

import com.anthony.calorie_counter.entity.AlimentModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimentDto implements Serializable {
    @NotBlank(message = "The name must not be empty.")
    private String name;
    @NotBlank(message = "The portion must not be empty.")
    private String portion;
    @NotNull(message = "The calories must not be null.")
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
