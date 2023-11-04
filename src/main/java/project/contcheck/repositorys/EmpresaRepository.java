package project.contcheck.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.contcheck.domain.Empresa;
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
