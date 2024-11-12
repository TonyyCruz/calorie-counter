package com.anthony.calorie_counter.utils;

import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.ForbiddenRequestException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.UUID;

public class UserAuthorizationManager {

    protected Jwt getJwtUser() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected UUID getPrincipalId() {
        return UUID.fromString(getJwtUser().getSubject());
    }

    protected boolean principalIsNotAdmin() {
        String[] roles = getJwtUser().getClaims().get("scope").toString().split(" ");
        return !Arrays.asList(roles).contains(UserRole.ROLE_ADMIN.name());
    }

    protected void checkAuthorization(UUID userId) {
        if (principalIsNotAdmin() && !getPrincipalId().equals(userId)) {
            throw new ForbiddenRequestException(ExceptionMessages.UNAUTHORIZED_TO_PERFORM_ACTION);
        }
    }
}
