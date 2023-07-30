package by.it.academy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AGE", nullable = false, length = 3)
    private Integer age;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Column(name = "LASTNAME", nullable = false, length = 40)
    private String lastname;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Column(name = "FIRSTNAME", nullable = false, length = 20)
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z]*$")
    @Column(name = "SURNAME", nullable = false, length = 40)
    private String surname;

    @Email
    @Column(name = "EMAIL", nullable = false, length = 3)
    private String email;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;
}
