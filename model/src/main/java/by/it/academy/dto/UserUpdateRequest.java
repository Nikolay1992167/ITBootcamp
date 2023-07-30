package by.it.academy.dto;

import by.it.academy.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {

    @NotNull
    private Long id;

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
