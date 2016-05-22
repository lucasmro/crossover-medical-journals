package com.crossover.medical.journals.core;

public enum UserRole {
    PUBLISHER("PUBLISHER"), SUBSCRIBER("SUBSCRIBER");

    final String value;

    private UserRole(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRole fromValue(String value) {
        for (final UserRole e : UserRole.values()) {
            if (e.value == value) {
                return e;
            }
        }

        return UserRole.SUBSCRIBER;
    }
}
