package project.contcheck.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "mes_ano")
    private String mesAno;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "empresa_id", nullable = false)
    @ManyToOne
    private Empresa empresa;
}
