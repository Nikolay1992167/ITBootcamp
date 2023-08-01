package by.it.academy.dto;

import by.it.academy.entities.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    @Size(max = 3)
    private Integer age;

    @NotBlank
    @Size(max = 40)
    private String lastname;

    @NotBlank
    @Size(max = 20)
    private String firstname;

    @NotBlank
    @Size(max = 40)
    private String surname;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotNull
    private Role role;
}
