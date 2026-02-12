package org.ashan.domain.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ashan.domain.entity.User;
import org.ashan.domain.enums.CommentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeVisibility {

    @NotNull(message = "Comment ID is required")
    private Long commentId;

    @NotNull(message = "User identification is required")
    private Long userId;

    @NotNull(message = "Comment status is required")
    private CommentStatus commentStatus;

}
