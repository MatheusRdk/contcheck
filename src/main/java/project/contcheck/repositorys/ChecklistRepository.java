package project.contcheck.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.contcheck.domain.Checklist;
@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
