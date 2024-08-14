package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.utils.SimpleFake;


public class RoleFactory {

    public static RoleModel createUserRole() {
        return new RoleModel(SimpleFake.randomLong(), UserRole.ROLE_USER);
    }

    public static RoleModel createAdminRole() {
        return new RoleModel(SimpleFake.randomLong(), UserRole.ROLE_ADMIN);
    }
}
