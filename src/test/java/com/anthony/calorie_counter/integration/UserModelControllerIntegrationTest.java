package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.user.PasswordAuthenticateDto;
import com.anthony.calorie_counter.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.utils.SimpleFake;
import com.anthony.calorie_counter.utils.factories.RoleFactory;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@DisplayName("Integration test for User API endpoints")
public class UserModelControllerIntegrationTest extends TestBase {

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        userRepository.deleteAll();
        super.setUp();
    }

    @Nested
    @DisplayName("User test cases")
    class UserTestCases {

        @Test
        @DisplayName("Test if is possible create a new user and receive status code 201.")
        void canCreateANewUser() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(newUser.getName()))
                    .andExpect(jsonPath("$.email").value(newUser.getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(newUser.getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if is possible find self user data by id and receive status code 200.")
        void canUserFindSelfDataById() throws Exception {
            String path = USER_URL + "/" + savedUser().getId();
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(savedUser().getName()))
                    .andExpect(jsonPath("$.email").value(savedUser().getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(savedUser().getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if a user can update his data by id and receive status code 200.")
        void canUserUpdateHisDataById() throws Exception {
            UserUpdateDto updateUser = UserFactory.userUpdateDto();
            String valueAsString = objectMapper.writeValueAsString(updateUser);
            String path = USER_URL + "/update/user/" + savedUser().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(updateUser.getName()))
                    .andExpect(jsonPath("$.email").value(updateUser.getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(updateUser.getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(updateUser.getEmail(), savedUser().getPassword())))
                    .andExpect(status().isOk()).andDo(print());
        }

        @Test
        @DisplayName("Test if a user can update his password by id and receive status code 200.")
        void canUserUpdateHisPasswordById() throws Exception {
            PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto();
            passwordUpdateDto.setOldPassword(savedUser().getPassword());
            passwordUpdateDto.setNewPassword(SimpleFake.password(8));
            String valueAsString = objectMapper.writeValueAsString(passwordUpdateDto);
            String path = USER_URL + "/update/password/" + savedUser().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(savedUser().getEmail(), passwordUpdateDto.getNewPassword())))
                    .andExpect(status().isOk()).andDo(print());
        }

        @Test
        @DisplayName("Test if a user can delete his account by id and receive status code 200.")
        void canUserDeleteHisAccountById() throws Exception {
            PasswordAuthenticateDto passwordAuthenticate = new PasswordAuthenticateDto(savedUser().getPassword());
            String valueAsString = objectMapper.writeValueAsString(passwordAuthenticate);
            String path = USER_URL + "/" + savedUser().getId();
            mockMvc.perform(delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isNoContent())
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(savedUser().getEmail(), savedUser().getPassword())))
                    .andExpect(status().is(401)).andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with empty name and receive status code 400.")
        void cannotCreateAnUserWithEmptyName() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setName("");
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("name"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value("The name must not be empty."))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with empty email and receive status code 400.")
        void cannotCreateAnUserWithEmptyEmail() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setEmail("");
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("email"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value("The email must not be empty."))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with invalid email and receive status code 400.")
        void cannotCreateAUserWithInvalidEmail() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setEmail("email.com");
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("email"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value("Invalid Email."))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with a registered email and receive status code 400.")
        void cannotCreateAUserWithRegisteredEmail() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setEmail(savedUser().getEmail());
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("email"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value("This email is already in use."))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with empty phone number and receive status code 400.")
        void cannotCreateAUserWithEmptyPhoneNumber() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setPhoneNumber("");
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("phoneNumber"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value("Invalid phone number."))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with invalid phone number and receive status code 400.")
        void cannotCreateAUserWithInvalidPhoneNumber() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            String path = USER_URL + "/register";
            String[] invalidPhoneNumbers = new String[]{"(36) 91991-5500", "91991-5500", "(36) 31991-5500", "(36) 9191-5500", "(36) 919951-55009"};
            for (String number : invalidPhoneNumbers) {
                newUser.setPhoneNumber(number);
                String valueAsString = objectMapper.writeValueAsString(newUser);
                mockMvc.perform(post(path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.path").value(path))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath("$.fieldError[0].fieldName").value("phoneNumber"))
                        .andExpect(jsonPath("$.fieldError[0].errorMessage").value("Invalid phone number."))
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with empty password and receive status code 400.")
        void cannotCreateAUserWithEmptyPassword() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            newUser.setPassword("");
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath("$.fieldError[0].fieldName").value("password"))
                    .andExpect(jsonPath("$.fieldError[0].errorMessage").value(
                            "The password must have at least 8 characters including at least one uppercase, one lowercase and a number."
                    ))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to create a user with invalid password and receive status code 400.")
        void cannotCreateAUserWithInvalidPassword() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            String path = USER_URL + "/register";
            String[] invalidPassword = new String[]{"1234Aa.", "1234aa.", "1234AA.", "MyTestPass.*"};
            for (String number : invalidPassword) {
                newUser.setPassword(number);
                String valueAsString = objectMapper.writeValueAsString(newUser);
                mockMvc.perform(post(path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.path").value(path))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath("$.fieldError[0].fieldName").value("password"))
                        .andExpect(jsonPath("$.fieldError[0].errorMessage").value(
                                "The password must have at least 8 characters including at least one uppercase, one lowercase and a number."
                        ))
                        .andDo(print());
            }
        }

        @Test @DisplayName("Test if throws an exception when trying to find another user by id and receive status code 403.")
        void cannotFindAnotherUserById() throws Exception {
            UUID invalidId = UUID.randomUUID();
            String path = USER_URL + "/" + invalidId;
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.ForbiddenRequest"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.UNAUTHORIZED_TO_PERDFORM_ACTION))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to update another user and receive status code 403.")
        void cannotUpdateAnotherUser() throws Exception {
            UUID invalidId = UUID.randomUUID();
            UserUpdateDto userUpdateDto = UserFactory.userUpdateDto();
            String valueAsString = objectMapper.writeValueAsString(userUpdateDto);
            String path = USER_URL + "/update/user/" + invalidId;
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class com.anthony.calorie_counter.exceptions.ForbiddenRequest")
                    )
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.UNAUTHORIZED_TO_PERDFORM_ACTION))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to update a user with invalid password and receive status code 400.")
        void cannotUpdateAUserWithInvalidPassword() throws Exception {
            PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto(savedUser().getPassword(), "");
            String path = USER_URL + "/update/password/" + savedUser().getId();
            String[] invalidPassword = new String[]{"1234Aa.", "1234aa.", "1234AA.", "MyTestPass.*"};
            for (String newPassword : invalidPassword) {
                passwordUpdateDto.setNewPassword(newPassword);
                String valueAsString = objectMapper.writeValueAsString(passwordUpdateDto);
                mockMvc.perform(put(path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                                .header("Authorization", userToken)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.path").value(path))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath("$.fieldError[0].fieldName").value("newPassword"))
                        .andExpect(jsonPath("$.fieldError[0].errorMessage").value(
                                "The password must have at least 8 characters including at least one uppercase, one lowercase and a number."
                        ))
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when trying to update another user password and receive status code 403.")
        void cannotUpdateAnotherUserPassword() throws Exception {
            PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto(savedAdmin().getPassword(), "1234Aa.bbPo");
            String valueAsString = objectMapper.writeValueAsString(passwordUpdateDto);
            String path = USER_URL + "/update/password/" + savedAdmin().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class com.anthony.calorie_counter.exceptions.ForbiddenRequest")
                    )
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.UNAUTHORIZED_TO_PERDFORM_ACTION))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to delete another user and receive status code 403.")
        void cannotDeleteAnotherUser() throws Exception {
            PasswordAuthenticateDto passwordAuthenticateDto = new PasswordAuthenticateDto(savedAdmin().getPassword());
            String valueAsString = objectMapper.writeValueAsString(passwordAuthenticateDto);
            String path = USER_URL + "/" + savedAdmin().getId();
            mockMvc.perform(delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", userToken)
                    )
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class com.anthony.calorie_counter.exceptions.ForbiddenRequest")
                    )
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.UNAUTHORIZED_TO_PERDFORM_ACTION))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when trying to access unauthorized routes and receive status code 403.")
        void cannotAccessUnauthorizedRoutes() throws Exception {
            String[] unauthorizedRoutes = new String[] {"/promote/", "/demote/"};
            for (String route : unauthorizedRoutes) {
                String path = USER_URL + route + savedAdmin().getId();
                mockMvc.perform(post(path).header("Authorization", userToken))
                        .andExpect(status().isForbidden())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("Admin test cases")
    class AdminTestCases {

        @Test @DisplayName("Test if an admin can promote a user to admin by id and receive status code 200.")
        void canAdminPromoteAnUserToAdminById() throws Exception {
            String path = USER_URL + "/promote/" + savedUser().getId();
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(savedUser().getName()))
                    .andExpect(jsonPath("$.email").value(savedUser().getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(savedUser().getPhoneNumber()))
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(2)))
                    .andExpect(jsonPath(
                            "$.roles[*].authority", Matchers.containsInAnyOrder(
                                    RoleFactory.createAdminRole().getAuthority(),
                                    RoleFactory.createUserRole().getAuthority()
                            ))
                    )
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andDo(print());
        }

        @Test @DisplayName("Test if an admin can demote an admin to user by id and receive status code 200.")
        void canAdminDemoteAnAdminToUserById() throws Exception {
            userRepository.findById(savedUser().getId())
                    .ifPresent(userModel -> {
                        userModel.addRole(RoleFactory.createAdminRole());
                        userRepository.save(userModel);
                    });
            String getUserPath = USER_URL + "/" + savedUser().getId();
            mockMvc.perform((get(getUserPath)).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(2)))
                    .andExpect(jsonPath(
                            "$.roles[*].authority", Matchers.containsInAnyOrder(
                                    RoleFactory.createAdminRole().getAuthority(),
                                    RoleFactory.createUserRole().getAuthority()
                            ))
                    );
            String demotePath = USER_URL + "/demote/" + savedUser().getId();
            mockMvc.perform(post(demotePath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(savedUser().getName()))
                    .andExpect(jsonPath("$.email").value(savedUser().getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(savedUser().getPhoneNumber()))
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value( RoleFactory.createUserRole()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andDo(print());
        }

        @Test @DisplayName("Test if an admin can get all users and receive status code 200.")
        void canAdminGetAllUsers() throws Exception {
            mockMvc.perform(get(USER_URL).header("Authorization", adminToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                    .andExpect(jsonPath(
                            "$.content[*].email",
                            Matchers.containsInAnyOrder(savedUser().getEmail(), savedAdmin().getEmail()))
                    )
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if is possible an admin find a user by id and receive status code 200.")
        void canAdminFindUserById() throws Exception {
            String path = USER_URL + "/" + savedUser().getId();
            mockMvc.perform(get(path).header("Authorization", adminToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(savedUser().getName()))
                    .andExpect(jsonPath("$.email").value(savedUser().getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(savedUser().getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if an admin can update a user data by id and receive status code 200.")
        void canAdminUpdateAnUserDataById() throws Exception {
            UserUpdateDto updateUser = UserFactory.userUpdateDto();
            String valueAsString = objectMapper.writeValueAsString(updateUser);
            String path = USER_URL + "/update/user/" + savedUser().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedUser().getId().toString()))
                    .andExpect(jsonPath("$.name").value(updateUser.getName()))
                    .andExpect(jsonPath("$.email").value(updateUser.getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(updateUser.getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(updateUser.getEmail(), savedUser().getPassword())))
                    .andExpect(status().isOk()).andDo(print());
        }

        @Test
        @DisplayName("Test if an admin can update a user password by id and receive status code 200.")
        void canAdminUpdateAnUserPasswordById() throws Exception {
            PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto();
            passwordUpdateDto.setOldPassword("");
            passwordUpdateDto.setNewPassword(SimpleFake.password(8));
            String valueAsString = objectMapper.writeValueAsString(passwordUpdateDto);
            String path = USER_URL + "/update/password/" + savedUser().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(savedUser().getEmail(), passwordUpdateDto.getNewPassword())))
                    .andExpect(status().isOk()).andDo(print());
        }

        @Test
        @DisplayName("Test if an admin can delete a user account by id and receive status code 200.")
        void canAdminDeleteAnUserAccountById() throws Exception {
            PasswordAuthenticateDto passwordAuthenticate = new PasswordAuthenticateDto("");
            String valueAsString = objectMapper.writeValueAsString(passwordAuthenticate);
            String path = USER_URL + "/" + savedUser().getId();
            mockMvc.perform(delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNoContent())
                    .andDo(print());
            mockMvc.perform(post(AUTH_LOGIN_URL).with(httpBasic(savedUser().getEmail(), savedUser().getPassword())))
                    .andExpect(status().is(401)).andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to find a user with invalid id and receive status code 404.")
        void cannotFindAUserByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            String path = USER_URL + "/" + invalidId;
            mockMvc.perform(get(path).header("Authorization", adminToken))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to update a user with invalid id and receive status code 404.")
        void cannotUpdateAUserByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            UserUpdateDto userUpdateDto = UserFactory.userUpdateDto();
            String valueAsString = objectMapper.writeValueAsString(userUpdateDto);
            String path = USER_URL + "/update/user/" + invalidId;
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to update a password with invalid id and receive status code 404.")
        void cannotUpdateAPasswordByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            PasswordUpdateDto passwordUpdateDto = new PasswordUpdateDto("", SimpleFake.password(8));
            String valueAsString = objectMapper.writeValueAsString(passwordUpdateDto);
            String path = USER_URL + "/update/password/" + invalidId;
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to delete a user with invalid id and receive status code 404.")
        void cannotDeleteAUserByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            PasswordAuthenticateDto passwordAuthenticate = new PasswordAuthenticateDto();
            String valueAsString = objectMapper.writeValueAsString(passwordAuthenticate);
            String path = USER_URL + "/" + invalidId;
            mockMvc.perform(delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to promote a user with invalid id and receive status code 404.")
        void cannotPromoteAUserByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            String path = USER_URL + "/promote/" + invalidId;
            mockMvc.perform(post(path).header("Authorization", adminToken))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }

        @Test @DisplayName("Test if throws an exception when trying to demote a user with invalid id and receive status code 404.")
        void cannotDemoteAUserByInvalidId() throws Exception {
            UUID invalidId = UUID.randomUUID();
            String path = USER_URL + "/demote/" + invalidId;
            mockMvc.perform(post(path).header("Authorization", adminToken))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFound"))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(ExceptionMessages.USER_NOT_FOUND_WITH_ID + invalidId))
                    .andDo(print());
        }
    }
}
