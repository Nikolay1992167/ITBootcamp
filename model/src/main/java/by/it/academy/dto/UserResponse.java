package by.it.academy.dto;

import by.it.academy.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private Integer age;

    private String lastname;

    private String firstname;

    private String surname;

    private String email;

    private Role role;
}
