package org.example.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserRequest implements Serializable {

    @NotBlank(message = "username is required")
    @Size(min = 5, max = 16, message = "username must be between 5 and 16 character")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 25, message = "password must be between 8 and 25 character")
    private String password;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private String address;
}
