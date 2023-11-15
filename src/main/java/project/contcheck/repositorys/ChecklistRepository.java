package project.contcheck.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.contcheck.domain.Checklist;
import project.contcheck.domain.Empresa;
import project.contcheck.util.Status;
import project.contcheck.util.Tipo;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByTipo(Tipo tipo);
    List<Checklist> findByMesAno(String mesAno);
    List<Checklist> findByStatus(Status status);
    List<Checklist> findByEmpresa(Empresa empresa);
    @Query("select c from Checklist c where c.empresa.cnpj = ?1")
    List<Checklist> findByCnpj(String cnpj);
    @Modifying
    @Query("delete from Checklist c where c.empresa.id in (select e.id from Empresa e where e.cnpj = ?1)")
    int deleteAllByCnpj(String cnpj);
}