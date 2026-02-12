package org.ashan.domain.dto.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String name;
    private String email;
    private String loginToken;
}
