package project.contcheck.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.contcheck.domain.Checklist;
import project.contcheck.util.Tipo;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByTipo(Tipo tipo);
    List<Checklist> findByMesAno(String mesAno);

}
