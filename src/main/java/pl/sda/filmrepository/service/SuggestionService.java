package pl.sda.filmrepository.service;

import pl.sda.filmrepository.model.Suggestion;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

import java.util.List;
import java.util.Optional;

public interface SuggestionService {
    Suggestion addSuggestion(CreateSuggestionDTO createSuggestionDTO);
    Iterable<Suggestion> findSuggestionByAuthor(String author);
    Optional<Suggestion> findById(Long id);
    void deleteSuggestionById(Long id);
    Iterable<Suggestion> getAllSuggestions();
    void addAll(List<CreateSuggestionDTO> suggestions);
}
