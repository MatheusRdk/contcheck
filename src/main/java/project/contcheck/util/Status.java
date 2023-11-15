package project.contcheck.util;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Status {
    @Enumerated(EnumType.STRING)
    COMPLETO,
    @Enumerated(EnumType.STRING)
    INCOMPLETO;

    public static Status fromString(String status) {
        for (Status s : values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }
}
