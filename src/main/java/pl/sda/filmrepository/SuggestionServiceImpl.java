package pl.sda.filmrepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

import java.util.Optional;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    private SuggestioRepository suggestioRepository;

    public SuggestionServiceImpl(SuggestioRepository suggestioRepository) {
        this.suggestioRepository = suggestioRepository;
    }

    @Override
    public Suggestion addSuggestion(CreateSuggestionDTO createSuggestionDTO) {
        Suggestion suggestion = new Suggestion();
        suggestion.setLink(createSuggestionDTO.getLink());
        suggestion.setTitle(createSuggestionDTO.getTitle());
        suggestion.setScore(createSuggestionDTO.getScore());
        suggestion.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
        return suggestioRepository.save(suggestion);
    }

    @Override
    public Iterable<Suggestion> findSuggestionByAuthor(String author) {
        return suggestioRepository.findByAuthor(author);
    }

    @Override
    public Optional<Suggestion> findById(Long id) {
        return suggestioRepository.findById(id);
    }

    @Override
    public void deleteSuggestionById(Long id) {
            suggestioRepository.deleteById(id);
    }

    @Override
    public Iterable<Suggestion> getAllSuggestions() {
        return suggestioRepository.findAll();
    }
}
