package by.it.academy.service;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import by.it.academy.entities.Role;
import by.it.academy.entities.User;
import by.it.academy.exception.ResourceNotFoundException;
import by.it.academy.mapper.UserMapper;
import by.it.academy.repositories.UserRepository;
import by.it.academy.servicesImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1L);
        user.setAge(23);
        user.setLastname("Smith");
        user.setFirstname("Jack");
        user.setSurname("Petrovich");
        user.setEmail("jack@email.com");
        user.setRole(Role.valueOf("CUSTOMER_USER"));

        userRequest = new UserRequest();
        userRequest.setAge(32);
        userRequest.setLastname("Petrov");
        userRequest.setFirstname("Ivan");
        userRequest.setSurname("Victorovich");
        userRequest.setEmail("ivan@mail.com");
        userRequest.setRole(Role.valueOf("SALE_USER"));

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setAge(45);
        userUpdateRequest.setLastname("Kirdan");
        userUpdateRequest.setFirstname("Yrij");
        userUpdateRequest.setSurname("Victorovich");
        userUpdateRequest.setEmail("yrij@mail.com");
        userUpdateRequest.setRole(Role.valueOf("ADMINISTRATOR"));

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setAge(23);
        userResponse.setLastname("Smith");
        userResponse.setFirstname("Jack");
        userResponse.setSurname("Petrovich");
        userResponse.setEmail("jack@email.com");
        userResponse.setRole(Role.valueOf("CUSTOMER_USER"));
    }

    @Test
    @DisplayName("Should return the User object.")
    public void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.buildUserResponse(any(User.class))).thenReturn(userResponse);
        UserResponse result = userService.getUserById(1L);
        assertEquals(userResponse, result);
        verify(userRepository).findById(1L);
        verify(userMapper).buildUserResponse(user);
    }

    @Test
    @DisplayName("Should throw the Exception.")
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should save the User object.")
    public void testCreateUser() {
        when(userMapper.buildUser(any(UserRequest.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.buildUserResponse(any(User.class))).thenReturn(userResponse);
        UserResponse result = userService.createUser(userRequest);
        assertEquals(userResponse, result);
        verify(userMapper).buildUser(userRequest);
        verify(userRepository).save(user);
        verify(userMapper).buildUserResponse(user);
    }

    @Test
    @DisplayName("Should return list of User objects.")
    public void testGetUsers() {
        List<User> users = Arrays.asList(user);
        List<UserResponse> usersResponses = Arrays.asList(userResponse);
        when(userRepository.findAllByOrderByEmailAsc(any(Pageable.class))).thenReturn(users);
        when(userMapper.buildUserResponse(any(User.class))).thenReturn(userResponse);
        List<UserResponse> result = userService.getUsers(Pageable.unpaged());
        assertEquals(usersResponses, result);
        verify(userRepository).findAllByOrderByEmailAsc(Pageable.unpaged());
        verify(userMapper).buildUserResponse(user);
    }

    @Test
    @DisplayName("Should update User object.")
    public void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.updateUser(userUpdateRequest);
        assertEquals("Kirdan", user.getLastname());
        assertEquals("Yrij", user.getFirstname());
        assertEquals("Victorovich", user.getSurname());
        assertEquals("yrij@mail.com", user.getEmail());
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should delete User object.")
    public void testDeleteUserById() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }
}
