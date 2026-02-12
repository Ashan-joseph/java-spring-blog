package org.ashan.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "User name id required")
    private String name;

    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(min = 8,message = "Password cannot be less than 8 characters")
    private String password;
}
