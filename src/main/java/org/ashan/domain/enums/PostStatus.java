package org.ashan.domain.enums;

public enum PostStatus {
    VISIBLE(1),
    HIDE(0);

    private final int value;

    PostStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PostStatus fromValue(int value) {
        for (PostStatus status : PostStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus value: " + value);
    }
}
