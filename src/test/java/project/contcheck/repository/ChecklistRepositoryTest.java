package project.contcheck.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import project.contcheck.domain.Checklist;
import project.contcheck.domain.Empresa;
import project.contcheck.repositorys.ChecklistRepository;
import project.contcheck.repositorys.EmpresaRepository;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for checklist repository")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChecklistRepositoryTest {
    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Empresa empresa;

    @BeforeEach
    void setup() {
        empresa = Empresa.builder()
                .nome("Teste")
                .id(1L)
                .cnpj("12345678912345")
                .build();
        empresaRepository.save(empresa);
    }

    @Test
    @DisplayName("findByTipo returns list of checklist when successful")
    void findByTipo_returnsListOfChecklist_WhenSuccessful() {
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
    @DisplayName("findByMesAno returns list of checklist when successful")
    void findByMesAno_returnsListOfChecklist_WhenSuccessful() {
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

    @Test
    @DisplayName("findByStatus returns list of checklist when successful")
    void findByStatus_returnsListOfChecklist_WhenSuccessful() {
        Checklist checklist = Checklist.builder()
                .id(1L)
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .status(Status.COMPLETO)
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        List<Checklist> byMesAno = checklistRepository.findByStatus(Status.fromString("completo"));

        Assertions.assertThat(byMesAno).isNotEmpty().contains(savedChecklist);
    }

    @Test
    @DisplayName("findByEmpresa returns list of checklist when successful")
    void findByEmpresa_returnsListOfChecklist_WhenSuccessful() {
        Checklist checklist = Checklist.builder()
                .id(1L)
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .status(Status.COMPLETO)
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        List<Checklist> byMesAno = checklistRepository.findByEmpresa(empresa);

        Assertions.assertThat(byMesAno).isNotEmpty().contains(savedChecklist);
    }

    @Test
    @DisplayName("findByCnpj returns list of checklist when successful")
    void findByCnpj_returnsListOfChecklist_WhenSuccessful() {
        Checklist checklist = Checklist.builder()
                .id(1L)
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        List<Checklist> byCnpj = checklistRepository.findByCnpj("12345678912345");

        Assertions.assertThat(byCnpj).isNotEmpty().contains(savedChecklist);
    }

    @Test
    @DisplayName("Delete removes checklist when succesful")
    void delete_RemovesChecklist_WhenSuccessful() {
        Checklist checklistToBeSaved = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklistToBeSaved);

        checklistRepository.delete(savedChecklist);
        Optional<Checklist> ChecklistOptional = checklistRepository.findById(savedChecklist.getId());

        Assertions.assertThat(ChecklistOptional).isEmpty();
    }

    @Test
    @DisplayName("deleteByCnpj removes list of checklist when successful")
    void deleteByCnpj_RemovesChecklist_WhenSuccessful() {
        Checklist checklist = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist checklist2 = Checklist.builder()
                .tipo(Tipo.FOLHA)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);
        Checklist savedChecklist2 = checklistRepository.save(checklist2);

        int deleted = checklistRepository.deleteAllByCnpj("12345678912345");

        Assertions.assertThat(deleted).isEqualTo(2);
        Assertions.assertThat(checklistRepository.findByCnpj("12345678912345").size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Save persists checklist when succesful")
    void save_PersistsChecklist_WhenSuccessful() {
        Checklist checklist = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist);

        Assertions.assertThat(savedChecklist).isNotNull();
        Assertions.assertThat(savedChecklist.getId()).isNotNull();
        Assertions.assertThat(savedChecklist.getMesAno()).isEqualTo(checklist.getMesAno());
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when empresa_id, tipo and mes_ano already exists")
        //This means that we cannot insert a new record of the same period and type for a company that already has this record.
    void save_ThrowDataIntegrityViolationException_WhenEmpresaMesAnoAndTipoAlreadyExists() {
        Checklist checklist1 = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.COMPLETO)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist1);

        Checklist checklist2 = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.INCOMPLETO)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();


        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> checklistRepository.save(checklist2));
    }

    @Test
    @DisplayName("Save persists checklist and throw nothing when empresa_id and mes_ano already exists, but tipo is new")
    void save_PersistsChecklistAndThrowNothing_WhenEmpresaAndMesAnoExistsButTipoDont() {
        Checklist checklist1 = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.COMPLETO)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist = checklistRepository.save(checklist1);

        Checklist checklist2 = Checklist.builder()
                .tipo(Tipo.FOLHA)
                .status(Status.COMPLETO)
                .mesAno("01/2022")
                .empresa(empresa)
                .build();

        Checklist savedChecklist2 = checklistRepository.save(checklist2);

        Assertions.assertThatCode(() -> checklistRepository.save(checklist2))
                .doesNotThrowAnyException();

        Assertions.assertThat(savedChecklist).isNotNull();
        Assertions.assertThat(savedChecklist.getId()).isNotNull();

        Assertions.assertThat(savedChecklist2).isNotNull();
        Assertions.assertThat(savedChecklist2.getId()).isNotNull();

        Assertions.assertThat(savedChecklist2.getId()).isNotEqualTo(savedChecklist.getId());
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when the mes_ano format is different from month/year")
    void save_ThrowDataIntegrityViolationException_WhenMesAnoIsWrongFormat() {
        Checklist checklist = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.COMPLETO)
                .mesAno("012022")
                .empresa(empresa)
                .build();

        Checklist checklist2 = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.COMPLETO)
                .mesAno("1/2022")
                .empresa(empresa)
                .build();

        Checklist checklist3 = Checklist.builder()
                .tipo(Tipo.CONTABILIDADE)
                .status(Status.COMPLETO)
                .mesAno("2022/01")
                .empresa(empresa)
                .build();

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> checklistRepository.save(checklist));
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> checklistRepository.save(checklist2));
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> checklistRepository.save(checklist3));
    }
}