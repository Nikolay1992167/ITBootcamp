package by.it.academy.servicesImpl;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import by.it.academy.entities.User;
import by.it.academy.exception.ResourceNotFoundException;
import by.it.academy.mapper.UserMapper;
import by.it.academy.repositories.UserRepository;
import by.it.academy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::buildUserResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not find."));
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.buildUser(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.buildUserResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAllByOrderByEmailAsc(pageable)
                .stream()
                .map(userMapper::buildUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User updateUser = userRepository.findById(userUpdateRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Not find user.")));
        updateUser.setAge(userUpdateRequest.getAge());
        updateUser.setFirstname(userUpdateRequest.getFirstname());
        updateUser.setLastname(userUpdateRequest.getLastname());
        updateUser.setSurname(userUpdateRequest.getSurname());
        updateUser.setEmail(userUpdateRequest.getEmail());
        updateUser.setRole(userUpdateRequest.getRole());
        userRepository.save(updateUser);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
