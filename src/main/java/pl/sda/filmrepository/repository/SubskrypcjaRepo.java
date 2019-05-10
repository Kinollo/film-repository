package pl.sda.filmrepository.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.filmrepository.model.Subskrypcja;

public interface SubskrypcjaRepo extends CrudRepository <Subskrypcja, String> {
}
