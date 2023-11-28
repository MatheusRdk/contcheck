package project.contcheck.controller;

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
import project.contcheck.service.ChecklistService;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for checklist controller")
public class ChecklistControllerTest {
    @InjectMocks
    private ChecklistController checklistController;
    @Mock
    private ChecklistService checklistService;
    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = Empresa.builder().cnpj("12345678912983").nome("EmpresaTeste").id(1L).build();
        List<Checklist> checkList = new ArrayList<>(List
                .of(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build()));
        BDDMockito.when(checklistService.findByIdOrThrowNotFoundException(ArgumentMatchers.anyLong()))
                .thenReturn(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build());
        BDDMockito.when(checklistService.listAll()).thenReturn(checkList);
        BDDMockito.when(checklistService.findByStatus(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistService.findChecklistByCnpj(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistService.findByMesAno(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistService.findByTipo(ArgumentMatchers.anyString())).thenReturn(checkList);
        BDDMockito.when(checklistService.save(ArgumentMatchers.any(ChecklistPostRequestBody.class)))
                .thenReturn(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build());
        BDDMockito.doNothing().when(checklistService).deleteAllChecklistByCnpj(ArgumentMatchers.anyString());
        BDDMockito.doNothing().when(checklistService).deleteChecklistById(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("listAll returns list of checklists when successful")
    void listAll_ReturnsListOfChecklists_WhenSuccessful() {
        List<Checklist> checklists = checklistController.listAll().getBody();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getTipo()).isEqualTo(Tipo.fromString("Contabilidade"));
    }

    @Test
    @DisplayName("findById returns checklist when successful")
    void findById_ReturnsChecklist_WhenSuccessful() {
        Checklist checklistFound = checklistController.findById(1L).getBody();

        Assertions.assertThat(checklistFound).isNotNull();
        Assertions.assertThat(checklistFound.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findByTipo returns list of checklist when successful")
    void findByTipo_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistController.findByTipo("Contabilidade").getBody();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findByMesAno returns list of checklist when successful")
    void findByMesAno_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistController.findByMesAno("01/2022").getBody();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findByStatus returns list of checklist when successful")
    void findByStatus_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistController.findByStatus("Completo").getBody();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("findByCnpj returns list of checklist when successful")
    void findByCnpj_ReturnsListOfChecklist_WhenSuccessful() {
        List<Checklist> checklists = checklistController.findByCnpj("11425879546231").getBody();

        Assertions.assertThat(checklists).isNotNull();
        Assertions.assertThat(checklists).isNotEmpty().hasSize(1);
        Assertions.assertThat(checklists.get(0).getEmpresa()).isEqualTo(empresa);
    }

    @Test
    @DisplayName("save returns checklist when successful")
    void save_ReturnsChecklist_WhenSuccessful() {
        ChecklistPostRequestBody checklistPostRequestBody = new ChecklistPostRequestBody("Contabilidade", "01/2022", "Completo", 1L);
        Checklist savedChecklist = checklistController.save(checklistPostRequestBody).getBody();

        Assertions.assertThat(savedChecklist).isNotNull()
                .isEqualTo(Checklist.builder().id(1L).tipo(Tipo.CONTABILIDADE).empresa(empresa).mesAno("01/2022").status(Status.COMPLETO).build());
    }


    @Test
    @DisplayName("delete removes checklist when successful")
    void delete_RemovesChecklist_WhenSuccessful() {
        Assertions.assertThatCode(() -> checklistController.deleteById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("deleteAllByCnpj removes checklist when successful")
    void deleteAllByCnpj_RemovesChecklist_WhenSuccessful() {
        Assertions.assertThatCode(() -> checklistController.deleteAllByCnpj("12345645678912"))
                .doesNotThrowAnyException();
        BDDMockito.verify(checklistService, BDDMockito.times(1)).deleteAllChecklistByCnpj("12345645678912");
    }
}