package project.contcheck.repositorys;

import org.springframework.data.repository.CrudRepository;
import project.contcheck.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
