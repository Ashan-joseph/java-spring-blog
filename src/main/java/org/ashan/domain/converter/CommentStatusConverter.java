package org.ashan.domain.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.ashan.domain.enums.CommentStatus;
import org.ashan.domain.enums.UserStatus;

@Converter(autoApply = false)
public class CommentStatusConverter implements AttributeConverter<CommentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CommentStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public CommentStatus convertToEntityAttribute(Integer dbValue) {
        return dbValue == null ? null : CommentStatus.fromValue(dbValue);
    }
}
