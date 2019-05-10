package pl.sda.filmrepository.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.filmrepository.model.Suggestion;

public interface SuggestioRepository extends CrudRepository<Suggestion, Long> {
    Iterable<Suggestion> findByAuthor(String author);
}
