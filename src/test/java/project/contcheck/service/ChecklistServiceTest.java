package project.contcheck.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.contcheck.domain.Checklist;
import project.contcheck.domain.Empresa;
import project.contcheck.dto.ChecklistPostRequestBody;
import project.contcheck.exceptions.ChecklistNotFoundException;
import project.contcheck.exceptions.EmpresaNotFoundException;
import project.contcheck.repositorys.ChecklistRepository;
import project.contcheck.repositorys.EmpresaRepository;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for checklist service")
public class ChecklistServiceTest {
    @InjectMocks
    private ChecklistService checklistService;

    @Mock
    private ChecklistRepository checklistRepositoryMock;

    @Mock
    private EmpresaRepository empresaRepositoryMock;

    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = Empresa.builder().cnpj("12345678912983").nome("EmpresaTeste").id(1L).build();
        List<Checklist> checkList = new ArrayList<>(List
                .of(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build()));
        BDDMockito.when(empresaRepositoryMock.findById(1L)).thenReturn(Optional.of(empresa));
        BDDMockito.when(checklistRepositoryMock.findAll()).thenReturn(checkList);
        BDDMockito.when(checklistRepositoryMock.findById(1L))
                .thenReturn(Optional.of(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build()));
        BDDMockito.when(checklistRepositoryMock.findByStatus(Status.COMPLETO)).thenReturn(checkList);
        BDDMockito.when(checklistRepositoryMock.findByCnpj(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistRepositoryMock.findByMesAno(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistRepositoryMock.findByTipo(Tipo.CONTABILIDADE)).thenReturn(checkList);
        BDDMockito.when(checklistRepositoryMock.save(ArgumentMatchers.any(Checklist.class)))
                .thenReturn(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build());
        BDDMockito.doNothing().when(checklistRepositoryMock).delete(ArgumentMatchers.any(Checklist.class));
        BDDMockito.when(checklistRepositoryMock.deleteAllByCnpj(ArgumentMatchers.anyString()))
                .thenReturn(1);
        BDDMockito.when(checklistRepositoryMock.deleteAllByCnpj("00000000000000"))
                .thenReturn(0);
    }

    @Test
    @DisplayName("listAll returns list of checklists when successful")
    void listAll_ReturnsListOfChecklists_WhenSuccessful() {
        List<Checklist> checklists = checklistService.listAll();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getTipo()).isEqualTo(Tipo.fromString("Contabilidade"));
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns checklist when successful")
    void findByIdOrThrowBadRequestException_ReturnsChecklist_WhenSuccessful() {
        Checklist checklistFound = checklistService.findByIdOrThrowNotFoundException(1L);

        Assertions.assertThat(checklistFound).isNotNull();
        Assertions.assertThat(checklistFound.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws exception when checklist is not found")
    void findByIdOrThrowBadRequestException_ThrowsException_WhenChecklistIsNotFound() {
        Assertions.assertThatExceptionOfType(ChecklistNotFoundException.class)
                .isThrownBy(() -> checklistService.findByIdOrThrowNotFoundException(2L));
    }

    @Test
    @DisplayName("findByTipo returns list of checklist when successful")
    void findByTipo_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistService.findByTipo("Contabilidade");

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findByMesAno returns list of checklist when successful")
    void findByMesAno_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistService.findByMesAno("01/2022");

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findByStatus returns list of checklist when successful")
    void findByStatus_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistService.findByStatus("Completo");

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findChecklistByCnpj returns list of checklist when successful")
    void findChecklistByCnpj_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistService.findChecklistByCnpj("11425879546231");

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("save returns checklist when successful")
    void save_ReturnsChecklist_WhenSuccessful() {
        ChecklistPostRequestBody checklistPostRequestBody = new ChecklistPostRequestBody("Contabilidade", "01/2022", "Completo", 1L);
        Checklist savedChecklist = checklistService.save(checklistPostRequestBody);

        Assertions.assertThat(savedChecklist).isNotNull()
                .isEqualTo(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build());
    }

    @Test
    @DisplayName("save throws exception when empresaId is not found")
    void save_ThrowsException_WhenEmpresaIdIsNotFound() {
        ChecklistPostRequestBody checklistPostRequestBody = new ChecklistPostRequestBody("Contabilidade", "01/2022", "Completo", 2L);
        Assertions.assertThatExceptionOfType(EmpresaNotFoundException.class)
                .isThrownBy(() -> checklistService.save(checklistPostRequestBody)).withMessage("Empresa Id not found");
    }

    @Test
    @DisplayName("delete removes checklist when successful")
    void delete_RemovesChecklist_WhenSuccessful() {
        Assertions.assertThatCode(() -> checklistService.deleteChecklistById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("deleteAllChecklistByCnpj removes checklist when successful")
    void deleteAllChecklistByCnpj_RemovesChecklist_WhenSuccessful() {
        Assertions.assertThatCode(() -> checklistService.deleteAllChecklistByCnpj("12345645678912"))
                .doesNotThrowAnyException();
        BDDMockito.verify(checklistRepositoryMock, BDDMockito.times(1)).deleteAllByCnpj("12345645678912");
    }

    @Test
    @DisplayName("deleteAllChecklistByCnpj throws exception when empresa cnpj is not found")
    void deleteAllChecklistByCnpj_throwsException_WhenEmpresaCnpjIsNotFound() {
        Assertions.assertThatExceptionOfType(EmpresaNotFoundException.class)
                .isThrownBy(() -> checklistService.deleteAllChecklistByCnpj("00000000000000")).withMessage("Cnpj não encontrado");
        BDDMockito.verify(checklistRepositoryMock, BDDMockito.times(1)).deleteAllByCnpj("00000000000000");
    }

}
