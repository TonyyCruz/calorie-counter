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
	void testCanSaveAnNewUser() {
		Long fakeId = 1L;
		User userToSave = buildUser();
		User expectUser = buildUser(fakeId);
		when(userRepository.save(userToSave)).thenReturn(expectUser);
		User receivedUser = userService.save(userToSave);
		assertEquals(userToSave.getFullName(), receivedUser.getFullName());
		assertEquals(userToSave.getEmail(), receivedUser.getEmail());
		assertEquals(userToSave.getPassword(), receivedUser.getPassword());
		assertEquals(expectUser.getId(), receivedUser.getId());
	}

	@Test
	void testCanUpdateAnUser() {
		Long userId = 10L;
		User currentUser = buildUser(userId);
		User expectUser = buildUser(userId);
		expectUser.setFullName("New Name");
		expectUser.setEmail("new@email.com");
		expectUser.setPassword("myNewPass01");
		when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
		when(userRepository.save(expectUser)).thenReturn(expectUser);
		expectUser.setId(0L);
		User receivedUser = userService.update(userId, expectUser);
		assertEquals(expectUser.getFullName(), receivedUser.getFullName());
		assertEquals(expectUser.getEmail(), receivedUser.getEmail());
		assertEquals(expectUser.getPassword(), receivedUser.getPassword());
		assertEquals(expectUser.getId(), receivedUser.getId());
	}

	// ======================================== Error cases ======================================== //
	@Test
	void testCannotFindUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
		when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
		Throwable error = assertThrowsExactly(NotFoundException.class , () -> userService.findById(invalidId));
		assertEquals(error.getMessage(), "User 99 was not found.");
	}

	@Test
	void testCannotUpdateUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
		User expectUser = buildUser();
		when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
		Throwable error = assertThrowsExactly(NotFoundException.class , () -> userService.update(invalidId, expectUser));
		assertEquals(error.getMessage(), "User 99 was not found.");
	}

	@Test
	void testCannotDeleteUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
		when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
		Throwable error = assertThrowsExactly(NotFoundException.class , () -> userService.delete(invalidId));
		assertEquals(error.getMessage(), "User 99 was not found.");
	}

	private User buildUser() {
		return new User(0L, "User Name", "Ab123456", "test@email.com");
	}

	private User buildUser(Long id) {
		return new User(id, "User Name", "Ab123456", "test@email.com");
	}
}
