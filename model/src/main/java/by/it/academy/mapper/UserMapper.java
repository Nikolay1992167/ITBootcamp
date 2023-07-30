package by.it.academy.mapper;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.entities.User;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class UserMapper {
    public UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .age(user.getAge())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User buildUser(UserRequest userRequest){
        return User.builder()
                .age(userRequest.getAge())
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .build();
    }
}
