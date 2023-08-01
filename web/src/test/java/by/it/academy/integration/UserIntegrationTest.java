package by.it.academy.integration;

import by.it.academy.dto.UserRequest;
import by.it.academy.dto.UserResponse;
import by.it.academy.dto.UserUpdateRequest;
import by.it.academy.entities.Role;
import by.it.academy.entities.User;
import by.it.academy.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserIntegrationTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private User firstUser;
    private User secondUser;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/user";

        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setAge(32);
        firstUser.setLastname("Petrov");
        firstUser.setFirstname("Ivan");
        firstUser.setSurname("Victorovich");
        firstUser.setEmail("ivan@mail.com");
        firstUser.setRole(Role.valueOf("SALE_USER"));

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setAge(45);
        secondUser.setLastname("Drozd");
        secondUser.setFirstname("Igor");
        secondUser.setSurname("Alexeevich");
        secondUser.setEmail("igor@mail.com");
        secondUser.setRole(Role.valueOf("SECURE_API_USER"));

        User saveFirstUser = userRepository.save(firstUser);
        User saveSecondUser = userRepository.save(secondUser);
    }

    @AfterEach
    public void afterSetup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateMovieTest() {
        UserRequest temporaryUser = new UserRequest();
        temporaryUser.setAge(45);
        temporaryUser.setLastname("Kirdan");
        temporaryUser.setFirstname("Yrij");
        temporaryUser.setSurname("Victorovich");
        temporaryUser.setEmail("yrij@mail.com");
        temporaryUser.setRole(Role.valueOf("ADMINISTRATOR"));

        UserRequest newUser = restTemplate.postForObject(baseUrl + "/create", temporaryUser, UserRequest.class);

        assertNotNull(newUser);
    }

    @Test
    void shouldGetUsersTest() {
        List<User> users = restTemplate.getForObject(baseUrl + "/all", List.class);
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void shouldGetOneUserByIdTest() {
        User existingUser = restTemplate.getForObject(baseUrl + "/" + firstUser.getId(), User.class);
        Assertions.assertNotNull(existingUser);
        Assertions.assertEquals("Ivan", existingUser.getFirstname());
    }

    @Test
    void shouldDeleteUserTest() {
        restTemplate.delete(baseUrl + "/" + firstUser.getId());
        int count = userRepository.findAll().size();
        Assertions.assertEquals(1, count);
    }

    @Test
    void shouldUpdateUserTest() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1L);
        userUpdateRequest.setAge(45);
        userUpdateRequest.setLastname("Kirdan");
        userUpdateRequest.setFirstname("Yrij");
        userUpdateRequest.setSurname("Victorovich");
        userUpdateRequest.setEmail("yrij@mail.com");
        userUpdateRequest.setRole(Role.valueOf("ADMINISTRATOR"));

        restTemplate.put(baseUrl + "/update", userUpdateRequest, User.class);
        UserResponse existingUser = restTemplate.getForObject(baseUrl + "/1L", UserResponse.class);
        assertNotNull(existingUser);
    }


}
