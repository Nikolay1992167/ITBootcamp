package by.it.academy.services;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getUsers(Pageable pageable);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUserById(Long id);
}
