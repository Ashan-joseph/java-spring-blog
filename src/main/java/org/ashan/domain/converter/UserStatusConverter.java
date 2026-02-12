package org.ashan.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.ashan.domain.enums.UserStatus;

@Converter(autoApply = false)
public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer dbValue) {
        return dbValue == null ? null : UserStatus.fromValue(dbValue);
    }
}
