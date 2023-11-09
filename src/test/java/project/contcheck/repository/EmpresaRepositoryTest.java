package project.contcheck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.contcheck.domain.Empresa;
import project.contcheck.repositorys.EmpresaRepository;

import java.util.List;

@DataJpaTest
@DisplayName("Tests for empresa repository")
public class EmpresaRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("findByNome returns list of empresa when successful")
    void findByNome_returnsListOfEmpresa_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);
        List<Empresa> empresas = empresaRepository.findByNome("Teste");

        Assertions.assertThat(empresas).isNotEmpty().contains(savedEmpresa);
    }

    @Test
    @DisplayName("findByCnpj returns list of empresa when successful")
    void findByCnpj_returnsListOfEmpresa_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);
        List<Empresa> empresas = empresaRepository.findByCnpj("12345678912345");

        Assertions.assertThat(empresas).isNotEmpty().contains(savedEmpresa);
    }

    @Test
    @DisplayName("Save persists empresa when succesful")
    void save_PersistsEmpresa_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);

        Assertions.assertThat(savedEmpresa).isNotNull();
        Assertions.assertThat(savedEmpresa.getId()).isNotNull();
        Assertions.assertThat(savedEmpresa.getNome()).isEqualTo(empresa.getNome());
    }

    @Test
    @DisplayName("Save fails when cnpj size is different from 14")
    void save_Fails_WhenCnpjIsDifferenteFrom14(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("123456789123")
                .build();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> empresaRepository.save(empresa))
                .withMessageContaining("O CNPJ deve ter exatamente 14 caracteres");
    }

    @Test
    @DisplayName("updateNome updates empresa name when successful")
    void updateNome_updatesName_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);

        String newNome = "TesteUpdated";

        int affected = empresaRepository.updateNome("12345678912345", "TesteUpdated");


        Assertions.assertThat(affected).isEqualTo(1);
        Assertions.assertThat(empresaRepository.findByNome("TesteUpdated").size()).isEqualTo(1);
        Assertions.assertThat(empresaRepository.findByNome("TesteUpdated").get(0).getCnpj()).isEqualTo("12345678912345");
    }

    @Test
    @DisplayName("updateCnpj updates empresa cnpj when successful")
    void updateCnpj_updatesCnpj_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);

        String newCnpj = "11144578899630";

        int affected = empresaRepository.updateCnpj("11144578899630", savedEmpresa.getCnpj());
        entityManager.flush();
        entityManager.clear();

        Assertions.assertThat(affected).isEqualTo(1);
        Assertions.assertThat(empresaRepository.findByCnpj("11144578899630").size()).isEqualTo(1);
        Assertions.assertThat(empresaRepository.findByNome("Teste").get(0).getCnpj()).isEqualTo("11144578899630");
    }

    @Test
    @DisplayName("deleteByCnpj deletes empresa when successful")
    void deleteByCnpj_deletesEmpresa_WhenSuccessful(){
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);

        int affected = empresaRepository.deleteByCnpj("12345678912345");

        Assertions.assertThat(affected).isEqualTo(1);
        Assertions.assertThat(empresaRepository.findByNome("Teste").size()).isEqualTo(0);
    }
}