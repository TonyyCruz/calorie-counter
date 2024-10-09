package com.anthony.calorie_counter.entity.dto.request.user;

import com.anthony.calorie_counter.service.validation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto implements Serializable {
    private String oldPassword;
    @PasswordValid
    private String newPassword;
}
