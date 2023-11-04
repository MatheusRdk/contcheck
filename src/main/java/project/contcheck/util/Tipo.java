package project.contcheck.util;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Tipo {
    @Enumerated(EnumType.STRING)
    CONTABILIDADE,
    @Enumerated(EnumType.STRING)
    FISCAL,
    @Enumerated(EnumType.STRING)
    FOLHA;
}
