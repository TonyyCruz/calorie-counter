package com.anthony.calorie_counter.entity.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordAuthenticateDto implements Serializable {
    private String password;
}
