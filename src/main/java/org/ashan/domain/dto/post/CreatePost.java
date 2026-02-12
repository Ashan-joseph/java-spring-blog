package org.ashan.domain.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePost {

    @Size(max =30 ,message = "Title character limit is 30")
    @NotBlank(message = "Post title is required")
    private String title;

    @Size(max = 150,  message = "Description charatcer limit is 150")
    @NotBlank(message = "Post description is required")
    private String description;

    @NotNull(message = "Author identification required")
    private Long userId;
}
