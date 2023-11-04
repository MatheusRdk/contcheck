package project.contcheck.util;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Status {
    @Enumerated(EnumType.STRING)
    COMPLETO,
    @Enumerated(EnumType.STRING)
    INCOMPLETO;
}
