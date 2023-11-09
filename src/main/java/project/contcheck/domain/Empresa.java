package project.contcheck.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "A empresa deve ter um cnpj")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres")
    @Column(unique = true)
    private String cnpj;

    @NotEmpty(message = "A empresa deve ter um nome")
    private String nome;
}