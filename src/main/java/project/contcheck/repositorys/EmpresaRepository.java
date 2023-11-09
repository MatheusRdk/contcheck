package project.contcheck.repositorys;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.contcheck.domain.Empresa;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByNome(String nome);

    List<Empresa> findByCnpj(String cnpj);

    @Modifying
    @Transactional
    @Query("delete from Empresa e where e.cnpj = ?1")
    int deleteByCnpj(String cnpj);  //int pq retorna quantos registros foram afetados

    @Modifying
    @Transactional
    @Query("update Empresa e set e.nome = ?2 where e.cnpj = ?1")
    int updateNome(String cnpj, String nome);

    @Modifying
    @Transactional
    @Query("update Empresa e set e.cnpj = ?1 where e.cnpj = ?2")
    int updateCnpj(String cnpjNovo, String cnpjAntigo);
}
