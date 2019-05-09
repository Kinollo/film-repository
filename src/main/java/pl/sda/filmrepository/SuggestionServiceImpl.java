package pl.sda.filmrepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

import java.time.Instant;
import java.util.Optional;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    private SuggestioRepository suggestioRepository;
    private ApplicationEventPublisher eventPublisher;

    public SuggestionServiceImpl(SuggestioRepository suggestioRepository, ApplicationEventPublisher eventPublisher) {
        this.suggestioRepository = suggestioRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Suggestion addSuggestion(CreateSuggestionDTO createSuggestionDTO) {
        Suggestion suggestion = new Suggestion();
        suggestion.setLink(createSuggestionDTO.getLink());
        suggestion.setTitle(createSuggestionDTO.getTitle());
        suggestion.setScore(createSuggestionDTO.getScore());
        suggestion.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
        Suggestion createdSuggestion = suggestioRepository.save(suggestion);
        eventPublisher.publishEvent(new SuggestionCreatedEvent(Instant.now(), createdSuggestion));
        return createdSuggestion;
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
