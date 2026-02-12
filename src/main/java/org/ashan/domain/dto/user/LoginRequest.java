package org.ashan.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "User email is required.")
    @Email(message= "Invalid email format")
    private String email;

    @NotBlank(message = "User password field is required")
    @Size(min=8,message = "Password must have 8 characters")
    private String password;
}
