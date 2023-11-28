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
import project.contcheck.domain.Empresa;
import project.contcheck.dto.EmpresaNewCnpjRequestBody;
import project.contcheck.dto.EmpresaPostRequestBody;
import project.contcheck.service.EmpresaService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for empresa controller")
public class EmpresaControllerTest {
    @InjectMocks
    private EmpresaController empresaController;
    @Mock
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        List<Empresa> empresaList = new ArrayList<>(List.of(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build()));

        BDDMockito.when(empresaService.listAll()).thenReturn(empresaList);
        BDDMockito.when(empresaService.findByName(ArgumentMatchers.anyString())).thenReturn(empresaList);
        BDDMockito.when(empresaService.findEmpresaByCnpj(ArgumentMatchers.anyString())).thenReturn(empresaList);
        BDDMockito.when(empresaService.save(ArgumentMatchers.any(EmpresaPostRequestBody.class))).thenReturn(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build());
        BDDMockito.doNothing().when(empresaService).deleteByCnpj(ArgumentMatchers.anyString());
        BDDMockito.doNothing().when(empresaService).updateCnpj(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        BDDMockito.doNothing().when(empresaService).updateNomePorCnpj(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("listAll returns list of empresa when successful")
    void listAll_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaController.listAll().getBody();

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of empresa when successful")
    void findByName_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaController.findByName("Teste").getBody();

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByCnpj returns list of empresa when successful")
    void findByCnpj_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaController.findByCnpj("12345678912345").getBody();

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns empresa when successful")
    void save_ReturnsEmpresa_WhenSuccessful() {
        EmpresaPostRequestBody empresaPostRequestBody = new EmpresaPostRequestBody("12345678912345", "Teste");
        Empresa empresa = empresaController.save(empresaPostRequestBody).getBody();

        Assertions.assertThat(empresa).isNotNull().isEqualTo(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build());
    }

    @Test
    @DisplayName("deleteByCnpj removes empresa when successful")
    void deleteByCnpj_RemovesEmpresa_WhenSuccessful() {
        Assertions.assertThatCode(() -> empresaController.deleteByCnpj("12345645678912"))
                .doesNotThrowAnyException();
        BDDMockito.verify(empresaService, BDDMockito.times(1)).deleteByCnpj("12345645678912");
    }

    @Test
    @DisplayName("updateNomeByCnpj updates empresa when successful")
    void updateNomeByCnpj_UpdatesEmpresa_WhenSuccessful() {
        EmpresaPostRequestBody empresaPostRequestBody = new EmpresaPostRequestBody("12345678912345", "Teste2");

        Assertions.assertThatCode(() -> empresaController.updateNomeByCnpj(empresaPostRequestBody))
                .doesNotThrowAnyException();

        BDDMockito.verify(empresaService, BDDMockito.times(1)).updateNomePorCnpj("12345678912345", "Teste2");
    }

    @Test
    @DisplayName("updateCnpjByCnpj updates empresa when successful")
    void updateCnpjByCnpj_UpdatesEmpresa_WhenSuccessful() {
        EmpresaNewCnpjRequestBody empresaNewCnpjRequestBody = new EmpresaNewCnpjRequestBody("12345678912345", "92345878942345");

        Assertions.assertThatCode(() -> empresaController.updateCnpjByCnpj(empresaNewCnpjRequestBody))
                .doesNotThrowAnyException();

        BDDMockito.verify(empresaService, BDDMockito.times(1)).updateCnpj("12345678912345", "92345878942345");
    }
}