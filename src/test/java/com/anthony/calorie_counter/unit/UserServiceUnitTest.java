package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.NotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.impl.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
	@InjectMocks
	private UserService userService;

	@Mock
	UserRepository userRepository;

	@Test
	void testCanFindUserById() {
		User expectUser = buildUser();
		when(userRepository.findById(expectUser.getId())).thenReturn(Optional.of(expectUser));
		User receivedUser = userService.findById(expectUser.getId());
		assertEquals(expectUser, receivedUser);
	}

	@Test
	void testCannotFindUserByInvalidIdAndThrowsAnException() {
		Long invalidIUd = 99L;
		when(userRepository.findById(invalidIUd)).thenReturn(Optional.empty());
		Throwable error = assertThrowsExactly(NotFoundException.class , () -> userService.findById(invalidIUd));
		assertEquals(error.getMessage(), "User 99 was not found.");
	}

	User buildUser() {
		return new User(1L, "User Name", "Ab123456", "test@email.com");
	}

}
