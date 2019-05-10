package pl.sda.filmrepository.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.filmrepository.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
