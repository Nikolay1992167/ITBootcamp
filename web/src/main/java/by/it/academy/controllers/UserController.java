package by.it.academy.controllers;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import by.it.academy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<UserResponse> getUsers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @PostMapping("/create")
    public UserResponse createUser(@Validated @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/update")
    public void updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
