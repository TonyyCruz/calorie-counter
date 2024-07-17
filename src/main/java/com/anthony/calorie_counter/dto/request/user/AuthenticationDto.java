package com.anthony.calorie_counter.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto implements Serializable {
    private String username;
    private String password;

    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(getUsername(), getPassword());
    }
}
