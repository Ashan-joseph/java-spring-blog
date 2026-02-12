package org.ashan.domain.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    @NotNull(message = "Post id required")
    private Long postId;

    @NotNull(message = "User id required")
    private Long userId;

    @NotBlank(message = "User comment cannot be empty")
    @Size(max = 150,message = "User comment should be less that 150 characters")
    private String comment;

}
