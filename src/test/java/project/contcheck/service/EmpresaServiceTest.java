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
import project.contcheck.domain.Empresa;
import project.contcheck.dto.EmpresaPostResponseBody;
import project.contcheck.exceptions.EmpresaNotFoundException;
import project.contcheck.repositorys.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for empresa service")
public class EmpresaServiceTest {
    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepositoryMock;

    @BeforeEach
    void setUp() {
        List<Empresa> empresaList = new ArrayList<>(List.of(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build()));

        BDDMockito.when(empresaRepositoryMock.findAll()).thenReturn(empresaList);
        BDDMockito.when(empresaRepositoryMock.findByNome(ArgumentMatchers.anyString())).thenReturn(empresaList);
        BDDMockito.when(empresaRepositoryMock.findByCnpj(ArgumentMatchers.anyString())).thenReturn(empresaList);
        BDDMockito.when(empresaRepositoryMock.save(ArgumentMatchers.any(Empresa.class))).thenReturn(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build());
        BDDMockito.when(empresaRepositoryMock.deleteByCnpj(ArgumentMatchers.anyString()))
                .thenReturn(1);
        BDDMockito.when(empresaRepositoryMock.deleteByCnpj("00000000000000"))
                .thenReturn(0);
        BDDMockito.when(empresaRepositoryMock.updateNome(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(1);
        BDDMockito.when(empresaRepositoryMock.updateNome("00000000000000", "Teste"))
                .thenReturn(0);
        BDDMockito.when(empresaRepositoryMock.updateCnpj(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(1);
        BDDMockito.when(empresaRepositoryMock.updateCnpj("00000000000000", "00000000000000"))
                .thenReturn(0);
    }

    @Test
    @DisplayName("listAll returns list of empresa when successful")
    void listAll_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaService.listAll();

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of empresa when successful")
    void findByName_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaService.findByName("Teste");

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByCnpj returns list of empresa when successful")
    void findByCnpj_ReturnsListOfEmpresa_WhenSuccessful() {
        String expectedName = "Teste";
        List<Empresa> empresaList = empresaService.findByCnpj("12345678912345");

        Assertions.assertThat(empresaList).isNotNull();
        Assertions.assertThat(empresaList).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresaList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns empresa when successful")
    void save_ReturnsEmpresa_WhenSuccessful() {
        EmpresaPostResponseBody empresaPostResponseBody = new EmpresaPostResponseBody("12345678912345", "Teste");
        Empresa empresa = empresaService.save(empresaPostResponseBody);

        Assertions.assertThat(empresa).isNotNull().isEqualTo(Empresa.builder().nome("Teste").cnpj("12345678912345").id(1L).build());
    }

    @Test
    @DisplayName("delete removes empresa when successful")
    void delete_RemovesEmpresa_WhenSuccessful() {
        Assertions.assertThatCode(() -> empresaService.deleteByCnpj("12345645678912"))
                .doesNotThrowAnyException();
        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).deleteByCnpj("12345645678912");
    }

    @Test
    @DisplayName("delete throws exception when cnpj is not found")
    void delete_ThrowsException_WhenCnpjIsNotFound() {
        Assertions.assertThatExceptionOfType(EmpresaNotFoundException.class)
                .isThrownBy(() -> empresaService.deleteByCnpj("00000000000000"));

        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).deleteByCnpj("00000000000000");
    }

    @Test
    @DisplayName("updateNomePorCnpj updates empresa when successful")
    void updateNomePorCnpj_UpdatesEmpresa_WhenSuccessful() {
        Assertions.assertThatCode(() -> empresaService.updateNomePorCnpj("12345678912345","TesteUpdated"))
                .doesNotThrowAnyException();

        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).updateNome("12345678912345", "TesteUpdated");
    }

    @Test
    @DisplayName("updateNomePorCnpj throws exception when cnpj is not found")
    void updateNomePorCnpj_ThrowsException_WhenCnpjIsNotFound() {
        Assertions.assertThatExceptionOfType(EmpresaNotFoundException.class)
                .isThrownBy(() -> empresaService.updateNomePorCnpj("00000000000000", "Teste"));

        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).updateNome("00000000000000", "Teste");
    }

    @Test
    @DisplayName("updateCnpj updates empresa when successful")
    void updateCnpj_UpdatesEmpresa_WhenSuccessful() {
        Assertions.assertThatCode(() -> empresaService.updateCnpj("12345678912345","44477795463879"))
                .doesNotThrowAnyException();

        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).updateCnpj("12345678912345", "44477795463879");
    }

    @Test
    @DisplayName("updateCnpj throws exception when cnpj is not found")
    void updateCnpj_ThrowsException_WhenCnpjIsNotFound() {
        Assertions.assertThatExceptionOfType(EmpresaNotFoundException.class)
                .isThrownBy(() -> empresaService.updateCnpj("00000000000000", "00000000000000"));

        BDDMockito.verify(empresaRepositoryMock, BDDMockito.times(1)).updateCnpj("00000000000000", "00000000000000");
    }
}
