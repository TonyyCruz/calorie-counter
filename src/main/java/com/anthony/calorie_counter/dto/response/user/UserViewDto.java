package com.anthony.calorie_counter.dto.response.user;

import com.anthony.calorie_counter.entity.Role;
import com.anthony.calorie_counter.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;

    private Set<Role> roles = new HashSet<>();

    public UserViewDto(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.roles = user.getRoles();
    }
}
