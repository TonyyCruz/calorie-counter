package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserModelServiceUnitTest {
	@InjectMocks
	private UserService userService;

	@Mock
	UserRepository userRepository;

	@Test @DisplayName("Test if is possible find a user by id.")
	void testCanFindUserById() {
		UserModel expectUserModel = buildUser();
		when(userRepository.findById(expectUserModel.getId())).thenReturn(Optional.of(expectUserModel));
		UserModel receivedUserModel = userService.findById(expectUserModel.getId());
		assertEquals(expectUserModel, receivedUserModel);
	}

	@Test @DisplayName("Test if is possible save a new user.")
	void testCanSaveAnNewUser() {
		Long fakeId = 1L;
		UserModel userModelToSave = buildUser();
		UserModel expectUserModel = buildUser(fakeId);
		when(userRepository.save(userModelToSave)).thenReturn(expectUserModel);
		UserModel receivedUserModel = userService.save(userModelToSave);
		assertEquals(userModelToSave.getFullName(), receivedUserModel.getFullName());
		assertEquals(userModelToSave.getEmail(), receivedUserModel.getEmail());
		assertEquals(userModelToSave.getPassword(), receivedUserModel.getPassword());
		assertEquals(expectUserModel.getId(), receivedUserModel.getId());
	}

	@Test @DisplayName("Test if is possible update a user.")
	void testCanUpdateUserAnUser() {
		Long userId = 10L;
		UserModel currentUserModel = buildUser(userId);
		UserModel expectUserModel = buildUser(userId);
		expectUserModel.setFullName("New Name");
		expectUserModel.setEmail("new@email.com");
		expectUserModel.setPassword("myNewPass01");
		when(userRepository.getReferenceById(userId)).thenReturn(currentUserModel);
		when(userRepository.save(expectUserModel)).thenReturn(expectUserModel);
		UserModel receivedUserModel = userService.updateUser(userId, expectUserModel);
		assertEquals(expectUserModel.getFullName(), receivedUserModel.getFullName());
		assertEquals(expectUserModel.getEmail(), receivedUserModel.getEmail());
		assertEquals(expectUserModel.getPassword(), receivedUserModel.getPassword());
		assertEquals(expectUserModel.getId(), receivedUserModel.getId());
	}

	// ======================================== Error cases ======================================== //

	@Test @DisplayName("Test if service method 'find user by id' throws an exception with invalid id.")
	void testCannotFindUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
		when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.findById(invalidId));
		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
	}

	@Test @DisplayName("Test if service method 'update' thrown an exception with invalid id.")
	void testCannotUpdateUserUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
		UserModel expectUserModel = buildUser();
		when(userRepository.getReferenceById(invalidId)).thenThrow(new EntityNotFoundException());
		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.updateUser(invalidId, expectUserModel));
		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
	}

	@Test @DisplayName("Test if service method 'delete' thrown an exception with invalid id.")
	void testCannotDeleteByEmailUserByInvalidIdAndThrowsAnException() {
		Long invalidId = 99L;
//		doThrow(new EmptyResultDataAccessException(0)).when(userRepository).deleteById(invalidId);
		when(userRepository.existsById(invalidId)).thenReturn(false);
		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.deleteByEmail(invalidId));
		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
	}

	private UserModel buildUser() {

		return new UserModel(0L, "User Name", "Ab123456", "test@email.com", "(11) 91991-5500");
	}

	private UserModel buildUser(Long id) {

		return new UserModel(id, "User Name", "Ab123456", "test@email.com", "(11) 91991-5500");
	}
}
