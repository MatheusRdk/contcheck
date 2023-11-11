package project.contcheck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.contcheck.domain.Checklist;
import project.contcheck.domain.Empresa;
import project.contcheck.repositorys.ChecklistRepository;
import project.contcheck.repositorys.EmpresaRepository;
import project.contcheck.util.Tipo;

import java.util.List;

@DataJpaTest
@DisplayName("Tests for checklist repository")
public class ChecklistRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("findByNome returns list of empresa when successful")
    void findByTipo_returnsListOfChecklist_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .id(1L)
                .cnpj("12345678912345")
                .build();

        empresaRepository.save(empresa);

        Checklist checklist = Checklist.builder()
                .id(1L)
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        List<Checklist> contabilidade = checklistRepository.findByTipo(Tipo.fromString("Contabilidade"));

        Assertions.assertThat(contabilidade).isNotEmpty().contains(savedChecklist);
    }

    @Test
    @DisplayName("findByMesAno returns list of empresa when successful")
    void findByMesAno_returnsListOfChecklist_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .id(1L)
                .cnpj("12345678912345")
                .build();

        empresaRepository.save(empresa);

        Checklist checklist = Checklist.builder()
                .id(1L)
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        List<Checklist> byMesAno = checklistRepository.findByMesAno("01/2022");

        Assertions.assertThat(byMesAno).isNotEmpty().contains(savedChecklist);
    }
}