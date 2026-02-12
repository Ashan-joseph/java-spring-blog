package org.ashan.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ashan.domain.entity.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String title;
    private String description;
    private String author;
    private List<CommentDto> comments;
}
