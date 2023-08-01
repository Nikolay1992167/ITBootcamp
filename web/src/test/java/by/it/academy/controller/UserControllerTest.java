package by.it.academy.controller;

import by.it.academy.controllers.UserController;
import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import by.it.academy.entities.Role;
import by.it.academy.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private UserResponse userResponseSecond;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    public void beforeSetup() {

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
        userResponse.setAge(32);
        userResponse.setLastname("Petrov");
        userResponse.setFirstname("Ivan");
        userResponse.setSurname("Victorovich");
        userResponse.setEmail("ivan@mail.com");
        userResponse.setRole(Role.valueOf("SALE_USER"));

        userResponseSecond = new UserResponse();
        userResponseSecond.setId(2L);
        userResponseSecond.setAge(45);
        userResponseSecond.setLastname("Drozd");
        userResponseSecond.setFirstname("Igor");
        userResponseSecond.setSurname("Alexeevich");
        userResponseSecond.setEmail("igor@mail.com");
        userResponseSecond.setRole(Role.valueOf("SECURE_API_USER"));
    }

    @Test
    void testGetUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(userResponse);
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.age").value(userResponse.getAge()))
                .andExpect(jsonPath("$.lastname").value(userResponse.getLastname()));
    }

    @Test
    void testGetUsers() throws Exception {
        List<UserResponse> users = List.of(userResponse, userResponseSecond);
        when(userService.getUsers(PageRequest.of(0, 10))).thenReturn(users);
        mockMvc.perform(get("/user/all?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(userRequest)).thenReturn(userResponse);
        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userResponse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("ivan@mail.com"))
                .andExpect(jsonPath("$.firstname").value("Ivan"));
    }

    @Test
    void testUpdateUser() throws Exception {
        doNothing().when(userService).updateUser(userUpdateRequest);
        mockMvc.perform(put("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {

        doNothing().when(userService).deleteUserById(1L);
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }
}
