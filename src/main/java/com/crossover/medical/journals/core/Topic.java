package com.crossover.medical.journals.core;

public enum Topic {
    BIOLOGY("Biology"), //
    CARDIOLOGY("Cardiology"), //
    ENDOCRINOLOGY("Endocrinology"), //
    GENETICS("Genetics"), //
    INFECTIOUS_DISEASES("Infectious Diseases"), //
    MEDICAL("Medical"), //
    NEUROLOGY("Neurology"), //
    ONCOLOGY("Oncology"), //
    PEDIATRICS("Pediatrics"), //
    PHYSIOLOGY("Physiology");

    final String value;

    private Topic(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Topic fromValue(String value) {
        for (final Topic e : Topic.values()) {
            if (e.value == value) {
                return e;
            }
        }

        return Topic.MEDICAL;
    }
}
