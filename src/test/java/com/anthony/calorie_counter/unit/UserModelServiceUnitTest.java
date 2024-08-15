package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.impl.UserService;
import com.anthony.calorie_counter.utils.factories.RoleFactory;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserModelServiceUnitTest {
	@InjectMocks
	private UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test @DisplayName("Test if is possible find a user by id.")
	void testCanFindUserById() {
		UserModel expectUserModel = UserFactory.createUser();
		when(userRepository.findById(expectUserModel.getId())).thenReturn(Optional.of(expectUserModel));
		UserModel receivedUserModel = userService.findById(expectUserModel.getId());
		assertEquals(expectUserModel, receivedUserModel);
	}

	@Test @DisplayName("Test if is possible save a new user.")
	void testCanSaveAnNewUser() {
		UserCreateDto userCreateDto = UserFactory.createUserDto();
		String encryptedPassword = "new_encrypted_pass";
		UserCreateDto userCreateEncrypted = UserFactory.createUserDtoClone(userCreateDto);
		userCreateEncrypted.setPassword(encryptedPassword);
		UserModel expected = UserFactory.createUserFromDto(userCreateEncrypted);
		when(passwordEncoder.encode(userCreateDto.getPassword())).thenReturn(encryptedPassword);
		when(userRepository.save(userCreateEncrypted.toEntity())).thenReturn(expected);
		UserModel current = userService.create(userCreateDto.toEntity());
		assertEquals(userCreateDto.getName(), current.getName());
		assertEquals(userCreateDto.getEmail(), current.getEmail());
		assertEquals(current.getPassword(), encryptedPassword);
		assertEquals(expected.getId(), current.getId());
	}

	@Test @DisplayName("Test if is possible to update a user")
	void testCanUpdateAnUser() {
		UserModel currentUserModel = UserFactory.createUser();
		UserModel expectUserModel = UserFactory.createUser();
		expectUserModel.setId(currentUserModel.getId());
		expectUserModel.setName("New Name");
		expectUserModel.setEmail("new@email.com");
		expectUserModel.setPhoneNumber("(11) 95051-5050");
		expectUserModel.setPassword(currentUserModel.getPassword());
		when(userRepository.getReferenceById(currentUserModel.getId())).thenReturn(currentUserModel);
		when(userRepository.save(expectUserModel)).thenReturn(expectUserModel);
		UserModel receivedUserModel = userService.updateUser(currentUserModel.getId(), expectUserModel);
		assertEquals(expectUserModel.getName(), receivedUserModel.getName());
		assertEquals(expectUserModel.getEmail(), receivedUserModel.getEmail());
		assertEquals(expectUserModel.getPassword(), receivedUserModel.getPassword());
		assertEquals(expectUserModel.getId(), receivedUserModel.getId());
	}

	@Test @DisplayName("Test if is possible to update a password.")
	void testCanUpdateThePassword() {
		UserModel currentUser = UserFactory.createUser();
		UserModel expectUser = UserFactory.createUserClone(currentUser);
		String encryptedPassword = "new_encrypted_pass";
		expectUser.setPassword(encryptedPassword);
		String newPassword = "new_common_pass";
		when(passwordEncoder.encode(newPassword)).thenReturn(encryptedPassword);
		when(userRepository.getReferenceById(currentUser.getId())).thenReturn(currentUser);
		when(userRepository.save(expectUser)).thenReturn(expectUser);
		userService.updatePassword(currentUser.getId(), newPassword);
		verify(userRepository, times(1)).save(expectUser);
		verify(passwordEncoder, times(1)).encode(newPassword);
	}

//	// ======================================== Error cases ======================================== //
//
//	@Test @DisplayName("Test if service method 'find user by id' throws an exception with invalid id.")
//	void testCannotFindUserByInvalidIdAndThrowsAnException() {
//		Long invalidId = 99L;
//		when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
//		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.findById(invalidId));
//		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
//	}
//
//	@Test @DisplayName("Test if service method 'update' thrown an exception with invalid id.")
//	void testCannotUpdateUserUserByInvalidIdAndThrowsAnException() {
//		Long invalidId = 99L;
//		UserModel expectUserModel = UserFactory.createUser();
//		when(userRepository.getReferenceById(invalidId)).thenThrow(new EntityNotFoundException());
//		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.updateUser(invalidId, expectUserModel));
//		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
//	}
//
//	@Test @DisplayName("Test if service method 'delete' thrown an exception with invalid id.")
//	void testCannotDeleteByEmailUserByInvalidIdAndThrowsAnException() {
//		Long invalidId = 99L;
////		doThrow(new EmptyResultDataAccessException(0)).when(userRepository).deleteById(invalidId);
//		when(userRepository.existsById(invalidId)).thenReturn(false);
//		Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.deleteByEmail(invalidId));
//		assertEquals(error.getMessage(), "User " +  invalidId + " was not found.");
//	}
}
