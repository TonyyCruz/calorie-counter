package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.impl.UserService;
import com.anthony.calorie_counter.utils.factories.RoleFactory;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		UserModel expect = UserFactory.createUser();
		when(userRepository.findById(expect.getId())).thenReturn(Optional.of(expect));
		UserModel received = userService.findById(expect.getId());
		verify(userRepository, times(1)).findById(expect.getId());
		assertEquals(expect, received);
	}

	@Test @DisplayName("Test if is possible save a new user.")
	void testCanSaveAnNewUser() {
		UserCreateDto userCreateDto = UserFactory.createUserDto();
		String encryptedPassword = "new_encrypted_pass";
		UserModel expectProcess = UserFactory.createUserDtoClone(userCreateDto).toEntity();
		expectProcess.setPassword(encryptedPassword);
		RoleModel role = RoleFactory.createUserRole();
		expectProcess.addRole(role);
		UserModel expectedWithId = UserFactory.createUserClone(expectProcess);
		expectedWithId.setId(UUID.randomUUID());
		when(passwordEncoder.encode(userCreateDto.getPassword())).thenReturn(encryptedPassword);
		when(userRepository.save(expectProcess)).thenReturn(expectedWithId);
		UserModel received = userService.create(userCreateDto.toEntity());
		verify(userRepository, times(1)).save(expectProcess);
		verify(passwordEncoder, times(1)).encode(userCreateDto.getPassword());
		assertEquals(userCreateDto.getName(), received.getName());
		assertEquals(userCreateDto.getEmail(), received.getEmail());
		assertEquals(encryptedPassword, received.getPassword());
		assertEquals(expectedWithId.getId(), received.getId());
		assertEquals(1, received.getAuthorities().size());
		assertTrue(received.getRoles().contains(role));
	}

	@Test @DisplayName("Test if is possible to update a user")
	void testCanUpdateAnUser() {
		UserModel currentUserData = UserFactory.createUser();
		UserModel updateData = UserFactory.createUser();
		updateData.setId(currentUserData.getId());
		UserModel expected = UserFactory.createUserClone(currentUserData);
		expected.setName(updateData.getName());
		expected.setEmail(updateData.getEmail());
		expected.setPhoneNumber(updateData.getPhoneNumber());
		when(userRepository.getReferenceById(currentUserData.getId())).thenReturn(currentUserData);
		when(userRepository.save(expected)).thenReturn(expected);
		UserModel received = userService.updateUser(currentUserData.getId(), updateData);
		verify(userRepository, times(1)).save(updateData);
		verify(userRepository, times(1)).getReferenceById(currentUserData.getId());
		assertEquals(expected.getName(), received.getName());
		assertEquals(expected.getEmail(), received.getEmail());
		// THE PASSWORD CANNOT BE CHANGED HERE
		assertEquals(expected.getPassword(), currentUserData.getPassword());
		assertEquals(expected.getId(), received.getId());
	}

	@Test @DisplayName("Test if is possible to update a password.")
	void testCanUpdateThePassword() {
		UserModel userData = UserFactory.createUser();
		UserModel expect = UserFactory.createUserClone(userData);
		String newPassword = "new_common_pass";
		String encryptedPassword = "new_encrypted_pass";
		expect.setPassword(encryptedPassword);
		when(passwordEncoder.encode(newPassword)).thenReturn(encryptedPassword);
		when(userRepository.getReferenceById(userData.getId())).thenReturn(userData);
		when(userRepository.save(expect)).thenReturn(expect);
		userService.updatePassword(userData.getId(), newPassword);
		verify(userRepository, times(1)).save(expect);
		verify(userRepository, times(1)).getReferenceById(userData.getId());
		verify(passwordEncoder, times(1)).encode(newPassword);
	}

	@Test @DisplayName("Test if is possible delete a user.")
	void testCanDeleteUserById() {
		UserModel user = UserFactory.createUser();
		when(userRepository.existsById(user.getId())).thenReturn(true);
		doNothing().when(userRepository).deleteById(user.getId());
		userService.deleteById(user.getId());
		verify(userRepository, times(1)).existsById(user.getId());
		verify(userRepository, times(1)).deleteById(user.getId());
	}

	@Test @DisplayName("Test if is possible find all users.")
	void testCanFindAllUsers() {
		Pageable pageable = PageRequest.of(1, 1, Sort.Direction.ASC, "id");
		Page<UserModel> page = new PageImpl<UserModel>(List.of(UserFactory.createUser()));
		when(userRepository.findAll(pageable)).thenReturn(page);
		Page<UserModel> received = userService.findAll(pageable);
		verify(userRepository, times(1)).findAll(pageable);
		assertEquals(1, received.getSize());
	}

	@Test @DisplayName("Test if is possible promote a user to admin.")
	void testCanPromoteUserToAdmin() {
		UserModel commonUser = UserFactory.createUser();
		commonUser.addRole(RoleFactory.createUserRole());
		UserModel expect = UserFactory.createUserClone(commonUser);
		RoleModel roleAdmin = RoleFactory.createAdminRole();
		expect.addRole(roleAdmin);
		when(userRepository.getReferenceById(commonUser.getId())).thenReturn(commonUser);
		when(userRepository.save(expect)).thenReturn(expect);
		UserModel received = userService.promoteToAdmin(commonUser.getId());
		verify(userRepository, times(1)).getReferenceById(commonUser.getId());
		verify(userRepository, times(1)).save(expect);
		assertTrue(received.getRoles().contains(roleAdmin));
	}

	@Test @DisplayName("Test if is possible demote an admin to user.")
	void testCanDemoteAdminToUser() {
		UserModel adminUser = UserFactory.createUser();
		adminUser.addRole(RoleFactory.createUserRole());
		RoleModel roleAdmin = RoleFactory.createAdminRole();
		adminUser.addRole(roleAdmin);
		UserModel expect = UserFactory.createUserClone(adminUser);
		expect.removeRoleById(roleAdmin.getId());
		when(userRepository.getReferenceById(adminUser.getId())).thenReturn(adminUser);
		when(userRepository.save(expect)).thenReturn(expect);
		UserModel received = userService.promoteToAdmin(adminUser.getId());
		verify(userRepository, times(1)).getReferenceById(adminUser.getId());
		verify(userRepository, times(1)).save(expect);
		assertFalse(received.getRoles().contains(roleAdmin));
	}

	@Test @DisplayName("Test if is possible load by username.")
	void testCanLoadByUsername() {
		UserModel current = UserFactory.createUser();
		when(userRepository.findByEmail(current.getEmail())).thenReturn(Optional.of(current));
		UserModel received = userService.loadUserByUsername(current.getEmail());
		verify(userRepository, times(1)).findByEmail(current.getEmail());
		assertEquals(current, received);
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
