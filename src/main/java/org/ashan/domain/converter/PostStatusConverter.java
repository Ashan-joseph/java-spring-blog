package org.ashan.domain.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.ashan.domain.enums.PostStatus;

@Converter(autoApply = false)
public class PostStatusConverter implements AttributeConverter<PostStatus, Integer>{

    @Override
    public Integer convertToDatabaseColumn(PostStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public PostStatus convertToEntityAttribute(Integer dbValue) {
        return dbValue == null ? null : PostStatus.fromValue(dbValue);
    }
}
