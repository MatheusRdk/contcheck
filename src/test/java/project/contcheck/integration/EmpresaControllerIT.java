package project.contcheck.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.contcheck.domain.Empresa;
import project.contcheck.repositorys.EmpresaRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Integration tests for empresa controller")
@ActiveProfiles("test")
public class EmpresaControllerIT {
    @LocalServerPort
    int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private DataSource dataSource;

    //TODO Fix this integration test and create others, this is using the mariadb instance instead of h2, it should use h2.
    @Test
    public void testDataSource() throws SQLException {
        System.out.println("DataSource URL: " + dataSource.getConnection().getMetaData().getURL());
    }

    @Test
    @DisplayName("listAll returns list of empresa when successful")
    void listAll_ReturnsListOfEmpresa_WhenSuccessful() {
        Empresa empresa = Empresa.builder()
                .nome("Teste")
                .cnpj("12345678912345")
                .build();
        Empresa savedEmpresa = empresaRepository.save(empresa);
        String expectedName = savedEmpresa.getNome();

        List<Empresa> empresas = testRestTemplate.exchange("/empresa/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Empresa>>() {
        }).getBody();

        Assertions.assertThat(empresas).isNotNull();
        Assertions.assertThat(empresas).isNotEmpty().hasSize(1);
        Assertions.assertThat(empresas.get(0).getNome()).isEqualTo(expectedName);
    }

//    @Test
//    @DisplayName("findByName returns list of empresa when successful")
//    void findByName_ReturnsListOfEmpresa_WhenSuccessful() {
//        Empresa empresa = Empresa.builder()
//                .nome("Teste")
//                .cnpj("12345678912345")
//                .build();
//        Empresa savedEmpresa = empresaRepository.save(empresa);
//        String expectedName = savedEmpresa.getNome();
//
//        List<Empresa> empresas = testRestTemplate.exchange("/empresa/find?name=Teste", HttpMethod.GET, null, new ParameterizedTypeReference<List<Empresa>>() {
//        }).getBody();
//
//        Assertions.assertThat(empresas).isNotNull();
//        Assertions.assertThat(empresas).isNotEmpty().hasSize(1);
//        Assertions.assertThat(empresas.get(0).getNome()).isEqualTo(expectedName);
//    }
//
//    @Test
//    @DisplayName("findByCnpj returns list of empresa when successful")
//    void findByCnpj_ReturnsListOfEmpresa_WhenSuccessful() {
//        Empresa empresa = Empresa.builder()
//                .nome("Teste")
//                .cnpj("12345678912345")
//                .build();
//        Empresa savedEmpresa = empresaRepository.save(empresa);
//        String expectedName = savedEmpresa.getNome();
//
//        List<Empresa> empresas = testRestTemplate.exchange("/empresa/12345678912345", HttpMethod.GET, null, new ParameterizedTypeReference<List<Empresa>>() {
//        }).getBody();
//
//        Assertions.assertThat(empresas).isNotNull();
//        Assertions.assertThat(empresas).isNotEmpty().hasSize(1);
//        Assertions.assertThat(empresas.get(0).getNome()).isEqualTo(expectedName);
//    }
//    @Test
//    @DisplayName("save returns empresa when successful")
//    void save_ReturnsEmpresa_WhenSuccessful(){
//        EmpresaPostRequestBody empresaPostRequestBody = new EmpresaPostRequestBody("12345678912345", "Teste");
//
//        ResponseEntity<Empresa> entity = testRestTemplate.exchange("/empresa", HttpMethod.POST, new HttpEntity<>(empresaPostRequestBody), new ParameterizedTypeReference<Empresa>() {
//        });
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        Assertions.assertThat(entity.getBody()).isNotNull();
//        Assertions.assertThat(entity.getBody().getId()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("deleteByCnpj removes architect when successful")
//    void deleteByCnpj_RemovesEmpresa_WhenSuccessful(){
//        Empresa empresa = Empresa.builder()
//                .nome("Teste")
//                .cnpj("12345678912345")
//                .build();
//        Empresa savedEmpresa = empresaRepository.save(empresa);
//
//        ResponseEntity<Void> entity = testRestTemplate.exchange("/empresa/delete/12345678912345", HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
//        });
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//
//    @Test
//    @DisplayName("updateNomeByCnpj updates empresa name when successful")
//    void updateNomeByCnpj_UpdatesEmpresaName_WhenSuccessful(){
//        Empresa empresa = Empresa.builder()
//                .nome("Teste")
//                .cnpj("12345678912345")
//                .build();
//        Empresa savedEmpresa = empresaRepository.save(empresa);
//
//        EmpresaPostRequestBody empresaPostRequestBody = new EmpresaPostRequestBody("12345678912345", "Teste2");
//
//        ResponseEntity<Void> entity = testRestTemplate.exchange("/empresa/updateNome", HttpMethod.PUT, new HttpEntity<>(empresaPostRequestBody), new ParameterizedTypeReference<Void>() {
//        });
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        Assertions.assertThat(entity.getBody()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("updateNomeByCnpj updates empresa cnpj when successful")
//    void updateCnpj_UpdatesEmpresaCnpj_WhenSuccessful(){
//        Empresa empresa = Empresa.builder()
//                .nome("Teste")
//                .cnpj("12345678912345")
//                .build();
//        Empresa savedEmpresa = empresaRepository.save(empresa);
//
//        EmpresaNewCnpjRequestBody empresaNewCnpjRequestBody = new EmpresaNewCnpjRequestBody("02345678712325", "12345678912345");
//
//        ResponseEntity<Void> entity = testRestTemplate.exchange("/empresa/updateCnpj", HttpMethod.PUT, new HttpEntity<>(empresaNewCnpjRequestBody), new ParameterizedTypeReference<Void>() {
//        });
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        Assertions.assertThat(entity.getBody()).isNotNull();
//    }
}