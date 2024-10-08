package com.anthony.calorie_counter.dto.response.user;

import java.io.Serializable;

public record LoginResponseTokenDto(String token) implements Serializable {
}
