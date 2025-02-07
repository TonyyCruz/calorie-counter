package com.anthony.calorie_counter.unit;

import com.anthony.calorie_counter.controller.UserController;
import com.anthony.calorie_counter.entity.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.impl.UserService;
import com.anthony.calorie_counter.utils.factories.RoleFactory;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for user")
public class UserUnitTest {

	@Nested
	@DisplayName("User Controller unit tests")
	public class UserControllerTest {
		@InjectMocks
		UserController userController;
		@Mock
		UserService userService;

		@Test @DisplayName("Test if is possible create a new user")
		void testCanCreateANewUser() {
			UserCreateDto userCreateDto = UserFactory.userCreateDto();
			UserModel createdUser = UserFactory.createUserEntityFromDto(userCreateDto);
			createdUser.addRole(RoleFactory.createUserRole());
			UserViewDto expect = new UserViewDto(createdUser);
			when(userService.create(userCreateDto.toEntity())).thenReturn(createdUser);
			ResponseEntity<UserViewDto> received = userController.create(userCreateDto);
			UserViewDto current = Objects.requireNonNull(received.getBody());
			verify(userService, times(1)).create(userCreateDto.toEntity());
			assertNotNull(current.id());
			assertEquals(expect.id(), current.id());
			assertEquals(expect.name(), current.name());
			assertEquals(expect.email(), current.email());
			assertEquals(expect.phoneNumber(), current.phoneNumber());
			assertEquals(expect.roles(), current.roles());
		}
	}

	@Nested
	@DisplayName("User Service unit tests")
	public class UserServiceTest {
		@InjectMocks
		private UserService userService;

		@Mock
		UserRepository userRepository;

		@Mock
		private PasswordEncoder passwordEncoder;

		@Test @DisplayName("Test if is possible find a user by id.")
		void testCanFindUserById() {
			UserModel expect = UserFactory.createUserEntity();
			when(userRepository.findById(expect.getId())).thenReturn(Optional.of(expect));
			UserModel received = userService.findById(expect.getId());
			verify(userRepository, times(1)).findById(expect.getId());
			assertEquals(expect, received);
		}

		@Test @DisplayName("Test if is possible save a new user.")
		void testCanSaveANewUser() {
			UserCreateDto userCreateDto = UserFactory.userCreateDto();
			String encryptedPassword = "new_encrypted_pass";
			UserModel expectProcess = UserFactory.cloneUserCreateDto(userCreateDto).toEntity();
			expectProcess.setPassword(encryptedPassword);
			RoleModel role = RoleFactory.createUserRole();
			expectProcess.addRole(role);
			UserModel expectedWithId = UserFactory.cloneUser(expectProcess);
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
		void testCanUpdateAUser() {
			UserModel currentUserData = UserFactory.createUserEntity();
			UserModel updateData = UserFactory.createUserEntity();
			updateData.setId(currentUserData.getId());
			UserModel expected = UserFactory.cloneUser(currentUserData);
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
			UserModel userData = UserFactory.createUserEntity();
			UserModel expect = UserFactory.cloneUser(userData);
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
		void testCanDeleteAUserById() {
			UUID id = UUID.randomUUID();
			when(userRepository.existsById(id)).thenReturn(true);
			doNothing().when(userRepository).deleteById(id);
			userService.delete(id);
			verify(userRepository, times(1)).existsById(id);
			verify(userRepository, times(1)).deleteById(id);
		}

		@Test @DisplayName("Test if is possible find all users.")
		void testCanFindAllUsers() {
			Pageable pageable = PageRequest.of(1, 1, Sort.Direction.ASC, "id");
			Page<UserModel> page = new PageImpl<UserModel>(List.of(UserFactory.createUserEntity(), UserFactory.createUserEntity()));
			when(userRepository.findAll(pageable)).thenReturn(page);
			Page<UserModel> received = userService.findAll(pageable);
			verify(userRepository, times(1)).findAll(pageable);
			assertEquals(2, received.getSize());
		}

		@Test @DisplayName("Test if is possible promote a user to admin.")
		void testCanPromoteAUserToAdmin() {
			UserModel commonUser = UserFactory.createUserEntity();
			commonUser.addRole(RoleFactory.createUserRole());
			UserModel expect = UserFactory.cloneUser(commonUser);
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
		void testCanDemoteAnAdminToUser() {
			UserModel adminUser = UserFactory.createUserEntity();
			adminUser.addRole(RoleFactory.createUserRole());
			RoleModel roleAdmin = RoleFactory.createAdminRole();
			adminUser.addRole(roleAdmin);
			UserModel expect = UserFactory.cloneUser(adminUser);
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
			UserModel current = UserFactory.createUserEntity();
			when(userRepository.findByEmail(current.getEmail())).thenReturn(Optional.of(current));
			UserModel received = userService.loadUserByUsername(current.getEmail());
			verify(userRepository, times(1)).findByEmail(current.getEmail());
			assertEquals(current, received);
		}

		@Test @DisplayName("Test if service method 'findById' throws an exception with invalid id.")
		void testTryFindUserByInvalidIdThrowsAnException() {
			UUID invalidId = UUID.randomUUID();
			when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
			Throwable error = assertThrowsExactly(EntityDataNotFoundException.class , () -> userService.findById(invalidId));
			verify(userRepository, times(1)).findById(invalidId);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidId);
		}

		@Test @DisplayName("Test if service method 'update' thrown an exception with invalid id.")
		void testTryUpdateUserByInvalidIdThrowsAnException() {
			UserModel invalidUpdate = UserFactory.createUserEntity();
			UserModel reference = new UserModel();
			reference.setId(invalidUpdate.getId());
			when(userRepository.getReferenceById(invalidUpdate.getId())).thenReturn(reference);
			when(userRepository.save(invalidUpdate)).thenThrow(new EntityNotFoundException());
			Throwable error = assertThrowsExactly(
					EntityDataNotFoundException.class,
					() -> userService.updateUser(invalidUpdate.getId(), invalidUpdate)
			);
			verify(userRepository, times(1)).getReferenceById(invalidUpdate.getId());
			verify(userRepository, times(1)).save(invalidUpdate);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidUpdate.getId());
		}

		@Test @DisplayName("Test if service method 'updatePassword' thrown an exception with invalid id.")
		void testTryUpdatePasswordByInvalidIdThrowsAnException() {
			UUID invalidId = UUID.randomUUID();
			String password = "Password123!";
			UserModel reference = new UserModel();
			reference.setId(invalidId);
			when(userRepository.getReferenceById(invalidId)).thenReturn(reference);
			when(userRepository.save(reference)).thenThrow(new EntityNotFoundException());
			Throwable error = assertThrowsExactly(
					EntityDataNotFoundException.class,
					() -> userService.updatePassword(invalidId, password)
			);
			verify(userRepository, times(1)).getReferenceById(invalidId);
			verify(userRepository, times(1)).save(reference);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidId);
		}

		@Test @DisplayName("Test if service method 'delete' thrown an exception with invalid id.")
		void testTryDeleteByInvalidIdThrowsAnException() {
			UUID invalidId = UUID.randomUUID();
			when(userRepository.existsById(invalidId)).thenReturn(false);
			Throwable error = assertThrowsExactly(
					EntityDataNotFoundException.class,
					() -> userService.delete(invalidId)
			);
			verify(userRepository, times(1)).existsById(invalidId);
			verify(userRepository, times(0)).deleteById(invalidId);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidId);
		}

		@Test @DisplayName("Test if service method 'promoteToAdmin' thrown an exception with invalid id.")
		void testTryPromoteToAdminByInvalidIdThrowsAnException() {
			UUID invalidId = UUID.randomUUID();
			UserModel reference = new UserModel();
			reference.setId(invalidId);
			when(userRepository.getReferenceById(invalidId)).thenReturn(reference);
			when(userRepository.save(reference)).thenThrow(new EntityNotFoundException());
			Throwable error = assertThrowsExactly(
					EntityDataNotFoundException.class,
					() -> userService.promoteToAdmin(invalidId)
			);
			verify(userRepository, times(1)).getReferenceById(invalidId);
			verify(userRepository, times(1)).save(reference);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidId);
		}

		@Test @DisplayName("Test if service method 'demoteToAdmin' thrown an exception with invalid id.")
		void testTryDemoteToAdminByInvalidIdThrowsAnException() {
			UUID invalidId = UUID.randomUUID();
			UserModel reference = new UserModel();
			reference.setId(invalidId);
			when(userRepository.getReferenceById(invalidId)).thenReturn(reference);
			when(userRepository.save(reference)).thenThrow(new EntityNotFoundException());
			Throwable error = assertThrowsExactly(
					EntityDataNotFoundException.class,
					() -> userService.demoteFromAdmin(invalidId)
			);
			verify(userRepository, times(1)).getReferenceById(invalidId);
			verify(userRepository, times(1)).save(reference);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_ID +  invalidId);
		}

		@Test @DisplayName("Test if service method 'loadUserByUsername' throws an exception with invalid username.")
		void testTryFindUserByInvalidUsernameThrowsAnException() {
			String invalidUsername = "Username@email.com";
			when(userRepository.findByEmail(invalidUsername)).thenReturn(Optional.empty());
			Throwable error = assertThrowsExactly(
					UsernameNotFoundException.class ,
					() -> userService.loadUserByUsername(invalidUsername)
			);
			verify(userRepository, times(1)).findByEmail(invalidUsername);
			assertEquals(error.getMessage(), ExceptionMessages.USER_NOT_FOUND_WITH_USERNAME +  invalidUsername);
		}
	}
}
