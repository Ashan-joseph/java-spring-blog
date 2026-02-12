package org.ashan.domain.enums;

public enum CommentStatus {
    VISIBLE(1),
    HIDE(0);

    private final int value;

    CommentStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CommentStatus fromValue(int value) {
        for (CommentStatus status : CommentStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid CommentStatus value: " + value);
    }
}
