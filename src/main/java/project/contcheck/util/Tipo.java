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

    public static Tipo fromString(String tipo) {
        for (Tipo t : values()) {
            if (t.name().equalsIgnoreCase(tipo)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + tipo);
    }
}
